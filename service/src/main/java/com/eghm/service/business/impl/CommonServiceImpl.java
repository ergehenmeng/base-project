package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.cache.CacheProxyService;
import com.eghm.cache.CacheService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.LockConstant;
import com.eghm.dto.ext.StoreScope;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ProductType;
import com.eghm.enums.SelectType;
import com.eghm.exception.BusinessException;
import com.eghm.lock.RedisLock;
import com.eghm.mapper.*;
import com.eghm.model.*;
import com.eghm.service.business.CommonService;
import com.eghm.state.machine.access.AccessHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import com.eghm.state.machine.context.RefundNotifyContext;
import com.eghm.utils.DateUtil;
import com.eghm.utils.SpringContextUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.statistics.ProductStatisticsVO;
import com.eghm.vo.sys.SysAreaVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Service("commonService")
@AllArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final RedisLock redisLock;

    private final ItemMapper itemMapper;

    private final LineMapper lineMapper;

    private final VenueMapper venueMapper;

    private final OrderMapper orderMapper;

    private final ScenicMapper scenicMapper;

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final SysAreaMapper sysAreaMapper;

    private final VoucherMapper voucherMapper;

    private final HomestayMapper homestayMapper;

    private final VenueSiteMapper venueSiteMapper;

    private final ItemStoreMapper itemStoreMapper;

    private final RestaurantMapper restaurantMapper;

    private final CacheProxyService cacheProxyService;

    private final TravelAgencyMapper travelAgencyMapper;

    private final ScenicTicketMapper scenicTicketMapper;

    private final HomestayRoomMapper homestayRoomMapper;

    @Override
    public void checkMaxDay(String configNid, long maxValue) {
        long apiLong = sysConfigApi.getLong(configNid);
        if (maxValue > apiLong) {
            log.error("设置时间间隔超过[{}]天", apiLong);
            throw new BusinessException(ErrorCode.MAX_DAY, apiLong);
        }
    }

    @Override
    public <T> T getHandler(String orderNo, Class<T> clsHandler) {
        return this.getHandler(ProductType.prefix(orderNo), clsHandler);
    }

    @Override
    public void handlePayNotify(PayNotifyContext context) {
        redisLock.lockVoid(LockConstant.ORDER_LOCK + context.getOrderNo(), 10_000, () ->
                this.getHandler(context.getTradeNo(), AccessHandler.class).payNotify(context)
        );
    }

    @Override
    public void handleRefundNotify(RefundNotifyContext context) {
        Order order = orderMapper.selectOne(Wrappers.<Order>lambdaQuery().eq(Order::getTradeNo, context.getTradeNo()).last(CommonConstant.LIMIT_ONE));
        if (order == null) {
            log.error("订单不存在,无法执行退款回调 [{}]", context.getTradeNo());
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        // 在订单处理过程中, 最好使用一把锁增加锁范围防止重复处理, 同时由于退款回调可能先于退款申请(不需要审核的订单发起申请时会直接退), 所以这里使用订单号作为锁
        redisLock.lockVoid(LockConstant.ORDER_LOCK + order.getOrderNo(), 10_000, () ->
                this.getHandler(context.getTradeNo(), AccessHandler.class).refundNotify(context)
        );
    }

    @Override
    public <T> T getHandler(ProductType productType, Class<T> clsHandler) {
        return SpringContextUtil.getBean(productType.getValue() + clsHandler.getSimpleName(), clsHandler);
    }

    @Override
    public List<String> parseTags(List<SysDictItem> dictList, String tagIds) {
        List<String> tagList = Lists.newArrayListWithCapacity(4);
        if (CollUtil.isEmpty(dictList)) {
            log.error("数据字典为空,不做解析 [{}]", tagIds);
            return tagList;
        }
        String[] split = tagIds.split(",");
        for (String tagId : split) {
            dictList.stream().filter(sysDict -> sysDict.getHiddenValue() == Integer.parseInt(tagId))
                    .map(SysDictItem::getShowValue)
                    .findFirst().ifPresent(tagList::add);
        }
        return tagList;
    }

    @Override
    public void checkIllegal(Long merchantId) {
        if (this.checkIsIllegal(merchantId)) {
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
    }

    @Override
    public boolean checkIsIllegal(Long merchantId) {
        return this.checkIsIllegal(merchantId, SecurityHolder.getMerchantId());
    }

    @Override
    public boolean checkIsIllegal(Long merchantId, Long loginMerchantId) {
        if (loginMerchantId == null && merchantId == null) {
            return false;
        }
        if (loginMerchantId == null || !loginMerchantId.equals(merchantId)) {
            log.error("商户访问了非自己的数据 [{}] [{}]", loginMerchantId, merchantId);
            return true;
        }
        return false;
    }

    @Override
    public List<ProductStatisticsVO> dayAppend(ProductRequest request) {
        // 按月统计或按日统计
        List<ProductStatisticsVO> resultList = new ArrayList<>();
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, Integer> itemMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> lineMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> voucherMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> ticketMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> roomMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> siteMap = Maps.newHashMapWithExpectedSize(32);
            Map<String, Integer> allMap = Maps.newHashMapWithExpectedSize(32);
            if (request.getProductType() == null) {
                itemMap = this.getStatisticsMonthMap(request, ProductType.ITEM);
                lineMap = this.getStatisticsMonthMap(request, ProductType.LINE);
                voucherMap = this.getStatisticsMonthMap(request, ProductType.VOUCHER);
                ticketMap = this.getStatisticsMonthMap(request, ProductType.TICKET);
                roomMap = this.getStatisticsMonthMap(request, ProductType.HOMESTAY);
                siteMap = this.getStatisticsMonthMap(request, ProductType.VENUE);
            } else {
                allMap = this.getStatisticsMonthMap(request, request.getProductType());
            }
            long between = ChronoUnit.MONTHS.between(request.getStartDate(), request.getEndDate());
            for (int i = 0; i < between; i++) {
                String date = request.getStartDate().plusMonths(i).format(DateUtil.MIN_FORMAT);
                ProductStatisticsVO vo = new ProductStatisticsVO(date);
                vo.setAppendNum(itemMap.getOrDefault(date, 0) +
                        lineMap.getOrDefault(date, 0) +
                        voucherMap.getOrDefault(date, 0) +
                        ticketMap.getOrDefault(date, 0) +
                        roomMap.getOrDefault(date, 0) +
                        siteMap.getOrDefault(date, 0) +
                        allMap.getOrDefault(date, 0));
                resultList.add(vo);
            }
        } else {
            Map<LocalDate, Integer> itemMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> lineMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> voucherMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> ticketMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> roomMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> siteMap = Maps.newHashMapWithExpectedSize(32);
            Map<LocalDate, Integer> allMap = Maps.newHashMapWithExpectedSize(32);
            if (request.getProductType() == null) {
                itemMap = this.getStatisticsDateMap(request, ProductType.ITEM);
                lineMap = this.getStatisticsDateMap(request, ProductType.LINE);
                voucherMap = this.getStatisticsDateMap(request, ProductType.VOUCHER);
                ticketMap = this.getStatisticsDateMap(request, ProductType.TICKET);
                roomMap = this.getStatisticsDateMap(request, ProductType.HOMESTAY);
                siteMap = this.getStatisticsDateMap(request, ProductType.VENUE);
            } else {
                allMap = this.getStatisticsDateMap(request, request.getProductType());
            }
            long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
            for (int i = 0; i < between; i++) {
                LocalDate date = request.getStartDate().plusDays(i);
                ProductStatisticsVO vo = this.createProductStatistics(date, itemMap, lineMap, voucherMap, ticketMap, roomMap, siteMap, allMap);
                resultList.add(vo);
            }
        }
        return resultList;
    }

    @Override
    public List<BaseStoreResponse> getStoreList(List<StoreScope> scopeIds) {
        if (CollUtil.isNotEmpty(scopeIds)) {
            return Lists.newArrayListWithExpectedSize(1);
        }
        Map<ProductType, List<Long>> productMap = scopeIds.stream().collect(Collectors.groupingBy(StoreScope::getProductType, Collectors.mapping(StoreScope::getStoreId, Collectors.toList())));
        List<BaseStoreResponse> responseList = new ArrayList<>();
        List<Long> storeIds = productMap.get(ProductType.ITEM);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = itemStoreMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        storeIds = productMap.get(ProductType.TICKET);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = scenicMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        storeIds = productMap.get(ProductType.HOMESTAY);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = homestayMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        storeIds = productMap.get(ProductType.VOUCHER);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = restaurantMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        storeIds = productMap.get(ProductType.LINE);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = travelAgencyMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        storeIds = productMap.get(ProductType.VENUE);
        if (CollUtil.isNotEmpty(storeIds)) {
            List<BaseStoreResponse> itemStoreList = venueMapper.getStoreList(storeIds);
            responseList.addAll(itemStoreList);
        }
        return responseList;
    }

    @Override
    public Long getStoreId(Long productId, ProductType productType) {
        switch (productType) {
            case ITEM:
                Item item = itemMapper.selectById(productId);
                if (item == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return item.getStoreId();
            case TICKET:
                ScenicTicket ticket = scenicTicketMapper.selectById(productId);
                if (ticket == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return ticket.getScenicId();
            case LINE:
                Line line = lineMapper.selectById(productId);
                if (line == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return line.getTravelAgencyId();
            case VENUE:
                VenueSite site = venueSiteMapper.selectById(productId);
                if (site == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return site.getVenueId();
            case VOUCHER:
                Voucher voucher = voucherMapper.selectById(productId);
                if (voucher == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return voucher.getRestaurantId();
            case HOMESTAY:
                HomestayRoom homestayRoom = homestayRoomMapper.selectById(productId);
                if (homestayRoom == null) {
                    throw new BusinessException(ErrorCode.PRODUCT_COUPON_DOWN);
                }
                return homestayRoom.getHomestayId();
            default:
                return null;
        }
    }

    @Override
    public List<SysAreaVO> getTreeAreaList() {
        List<SysAreaVO> areaList = cacheProxyService.getAreaList();
        return this.treeBin(CommonConstant.ROOT, areaList);
    }

    @Override
    public List<SysAreaVO> getTreeAreaList(List<Integer> gradeList) {
        List<SysAreaVO> areaList = sysAreaMapper.getList(gradeList);
        return this.treeBin(CommonConstant.ROOT, areaList);
    }

    @Override
    public void praise(String key, String hashKey, Consumer<Boolean> consumer) {
        boolean praise = cacheService.getHashValue(key, hashKey) == null;
        if (praise) {
            cacheService.setHashValue(key, hashKey, CacheConstant.PLACE_HOLDER);
        } else {
            cacheService.deleteHashKey(key, hashKey);
        }
        consumer.accept(praise);
    }

    /**
     * 创建指定日期统计对象
     *
     * @param date 日期
     * @param itemMap 零售商品
     * @param lineMap 线路商品
     * @param voucherMap 优惠券商品
     * @param ticketMap 票商品
     * @param roomMap Homestay商品
     * @param siteMap venue商品
     * @param allMap 全部商品(与上面互斥)
     * @return vo
     */
    private ProductStatisticsVO createProductStatistics(LocalDate date, Map<LocalDate, Integer> itemMap,
                                                        Map<LocalDate, Integer> lineMap, Map<LocalDate, Integer> voucherMap,
                                                        Map<LocalDate, Integer> ticketMap, Map<LocalDate, Integer> roomMap,
                                                        Map<LocalDate, Integer> siteMap, Map<LocalDate, Integer> allMap) {
        ProductStatisticsVO vo = new ProductStatisticsVO(date);
        vo.setAppendNum(itemMap.getOrDefault(date, 0) +
                lineMap.getOrDefault(date, 0) +
                voucherMap.getOrDefault(date, 0) +
                ticketMap.getOrDefault(date, 0) +
                roomMap.getOrDefault(date, 0) +
                siteMap.getOrDefault(date, 0) +
                allMap.getOrDefault(date, 0));
        return vo;
    }

    private Map<LocalDate, Integer> getStatisticsDateMap(ProductRequest request, ProductType productType) {
        List<ProductStatisticsVO> productList = itemMapper.dayAppend(request, productType.getTableName());
        return productList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
    }

    private Map<String, Integer> getStatisticsMonthMap(ProductRequest request, ProductType productType) {
        List<ProductStatisticsVO> productList = itemMapper.dayAppend(request, productType.getTableName());
        return productList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateMonth, ProductStatisticsVO::getAppendNum));
    }

    /**
     * 设置子节点
     *
     * @param pid    父节点
     * @param voList 全部列表
     * @return list
     */
    private List<SysAreaVO> treeBin(Long pid, List<SysAreaVO> voList) {
        List<SysAreaVO> collectList = voList.stream().filter(parent -> pid.equals(parent.getPid())).collect(Collectors.toList());
        collectList.forEach(parent -> parent.setChildren(this.treeBin(parent.getId(), voList)));
        return collectList;
    }
}
