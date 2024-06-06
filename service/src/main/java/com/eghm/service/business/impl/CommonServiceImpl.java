package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.cache.CacheProxyService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.StoreScope;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.*;
import com.eghm.model.*;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.handler.access.AccessHandler;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.RefundNotifyHandler;
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
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Service("commonService")
@AllArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final SysConfigApi sysConfigApi;

    private final ItemMapper itemMapper;

    private final LineMapper lineMapper;

    private final VenueMapper venueMapper;

    private final VoucherMapper voucherMapper;

    private final ScenicMapper scenicMapper;

    private final VenueSiteMapper venueSiteMapper;

    private final ItemStoreMapper itemStoreMapper;

    private final HomestayMapper homestayMapper;

    private final RestaurantMapper restaurantMapper;

    private final TravelAgencyMapper travelAgencyMapper;

    private final ScenicTicketMapper scenicTicketMapper;

    private final HomestayRoomMapper homestayRoomMapper;

    private final CacheProxyService cacheProxyService;

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
        this.getHandler(context.getTradeNo(), AccessHandler.class).payNotify(context);
    }

    @Override
    public void handleRefundNotify(RefundNotifyContext context) {
        this.getHandler(context.getTradeNo(), AccessHandler.class).refundNotify(context);
    }

    @Override
    public <T> T getHandler(ProductType productType, Class<T> clsHandler) {
        return SpringContextUtil.getBean(productType.getValue() + clsHandler.getSimpleName(), clsHandler);
    }

    @Override
    public RefundNotifyHandler getRefundHandler(String orderNo) {
        String prefix = ProductType.prefix(orderNo).getValue();
        return SpringContextUtil.getBean(prefix + RefundNotifyHandler.class.getSimpleName(), RefundNotifyHandler.class);
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
        Map<LocalDate, Integer> itemMap = Maps.newHashMapWithExpectedSize(32);
        if (request.getProductType() == null || request.getProductType() == ProductType.ITEM) {
            List<ProductStatisticsVO> itemList = itemMapper.dayAppend(request);
            itemMap = itemList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
        }
        Map<LocalDate, Integer> lineMap = Maps.newHashMapWithExpectedSize(32);
        if (request.getProductType() == null || request.getProductType() == ProductType.LINE) {
            List<ProductStatisticsVO> lineList = lineMapper.dayAppend(request);
            lineMap = lineList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
        }
        Map<LocalDate, Integer> voucherMap = Maps.newHashMapWithExpectedSize(32);
        if (request.getProductType() == null || request.getProductType() == ProductType.VOUCHER) {
            List<ProductStatisticsVO> voucherList = voucherMapper.dayAppend(request);
            voucherMap = voucherList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
        }
        Map<LocalDate, Integer> ticketMap = Maps.newHashMapWithExpectedSize(32);
        if (request.getProductType() == null || request.getProductType() == ProductType.TICKET) {
            List<ProductStatisticsVO> ticketList = scenicTicketMapper.dayAppend(request);
            ticketMap = ticketList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
        }
        Map<LocalDate, Integer> roomMap = Maps.newHashMapWithExpectedSize(32);
        if (request.getProductType() == null || request.getProductType() == ProductType.VOUCHER) {
            List<ProductStatisticsVO> roomList = homestayRoomMapper.dayAppend(request);
            roomMap = roomList.stream().collect(Collectors.toMap(ProductStatisticsVO::getCreateDate, ProductStatisticsVO::getAppendNum));
        }
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        List<ProductStatisticsVO> resultList = new ArrayList<>();
        for (int i = 0; i < between; i++) {
            LocalDate date = request.getStartDate().plusDays(i);
            ProductStatisticsVO vo = new ProductStatisticsVO(date);
            vo.setAppendNum(itemMap.getOrDefault(date, 0) +
                    lineMap.getOrDefault(date, 0) +
                    voucherMap.getOrDefault(date, 0) +
                    ticketMap.getOrDefault(date, 0) +
                    roomMap.getOrDefault(date, 0));
            resultList.add(vo);
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
        return treeBin(CommonConstant.ROOT, areaList);
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
