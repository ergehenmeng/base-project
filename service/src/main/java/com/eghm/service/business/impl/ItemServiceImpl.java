package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.group.SkuRequest;
import com.eghm.dto.business.item.*;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChargeMode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.GroupBookingMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.mapper.ItemStoreMapper;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.evaluation.ApplauseRateVO;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.item.*;
import com.eghm.vo.business.item.express.ItemExpressVO;
import com.eghm.vo.business.item.express.StoreExpressVO;
import com.eghm.vo.business.item.express.TotalExpressVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.EXPRESS_WEIGHT;
import static com.eghm.enums.ErrorCode.ITEM_DOWN;

/**
 * @author 殿小二
 * @since 2023/3/6
 */
@Service("itemService")
@Slf4j
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    private final SysConfigApi sysConfigApi;

    private final ItemSkuService itemSkuService;

    private final ItemStoreMapper itemStoreMapper;

    private final ItemSpecService itemSpecService;

    private final ExpressTemplateService expressTemplateService;

    private final CouponService couponService;

    private final OrderEvaluationService orderEvaluationService;

    private final ItemExpressRegionService itemExpressRegionService;

    private final MemberCollectService memberCollectService;

    private final CommonService commonService;

    private final GroupBookingMapper groupBookingMapper;

    private final JsonService jsonService;

    @Override
    public Page<ItemResponse> getByPage(ItemQueryRequest request) {
        return itemMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<ItemResponse> getList(ItemQueryRequest request) {
        Page<ItemResponse> listPage = itemMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public void create(ItemAddRequest request) {
        this.checkSpec(request.getMultiSpec(), request.getSpecList());
        this.titleRedo(request.getTitle(), null, request.getStoreId());
        this.checkExpress(request.getExpressId(), request.getSkuList());

        Item item = DataUtil.copy(request, Item.class);
        item.setMerchantId(SecurityHolder.getMerchantId());
        item.setCreateDate(LocalDate.now());
        this.setMinMaxPrice(item, request.getSkuList());
        // 总销量需要添加虚拟销量
        item.setTotalNum(request.getSkuList().stream().filter(itemSkuRequest -> itemSkuRequest.getVirtualNum() != null).mapToInt(ItemSkuRequest::getVirtualNum).sum());
        itemMapper.insert(item);

        Map<String, Long> specMap = itemSpecService.insert(item, request.getSpecList());
        itemSkuService.insert(item, specMap, request.getSkuList());
    }

    @Override
    public void update(ItemEditRequest request) {
        this.checkSpec(request.getMultiSpec(), request.getSpecList());
        this.titleRedo(request.getTitle(), request.getId(), request.getStoreId());
        this.checkExpress(request.getExpressId(), request.getSkuList());
        Item select = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(select.getMerchantId());

        Item item = DataUtil.copy(request, Item.class);
        itemMapper.updateById(item);

        if (select.getBookingId() == null) {
            Map<String, Long> specMap = itemSpecService.update(item, request.getSpecList());
            itemSkuService.update(item, specMap, request.getSkuList());
        } else {
            log.info("该商品是拼团商品,请先删除拼团活动后再编辑规格信息 [{}] [{}]", select.getId(), select.getBookingId());
        }
    }

    @Override
    public ItemDetailResponse getDetailById(Long itemId) {
        Item item = this.selectByIdRequired(itemId);
        ItemDetailResponse response = DataUtil.copy(item, ItemDetailResponse.class);
        // 多规格才会保存规格配置信息
        if (Boolean.TRUE.equals(item.getMultiSpec())) {
            List<ItemSpec> spec = itemSpecService.getByItemId(itemId);
            List<ItemSpecResponse> specList = DataUtil.copy(spec, ItemSpecResponse.class);
            // 根据规格名分组
            LinkedHashMap<String, List<ItemSpecResponse>> specMap = specList.stream().collect(Collectors.groupingBy(ItemSpecResponse::getSpecName, LinkedHashMap::new, Collectors.toList()));
            response.setSpecMap(specMap);
        }
        List<ItemSku> skuList = itemSkuService.getByItemId(itemId);
        response.setSkuList(DataUtil.copy(skuList, ItemSkuResponse.class));
        return response;
    }

    @Override
    public Item selectById(Long itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    public Item selectByIdRequired(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            log.error("该零售商品已删除 [{}]", itemId);
            throw new BusinessException(ITEM_DOWN);
        }
        return item;
    }

    @Override
    public void checkBookingItem(Long itemId) {
        Item item = this.selectByIdRequired(itemId);
        if (item.getBookingId() != null) {
            log.error("该商品已存在拼团活动 [{}]", item.getId());
            throw new BusinessException(ErrorCode.ITEM_BOOKING);
        }
        commonService.checkIllegal(item.getMerchantId());
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getState, state);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Item::getMerchantId, merchantId);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void updateGroupBooking(Long id, Boolean groupBooking, Long bookingId) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getBookingId, bookingId);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getSortBy, sortBy);
        itemMapper.update(null, wrapper);
    }

    @Override
    public Map<Long, Item> getByIdShelveMap(Set<Long> ids) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(Item::getId, ids);
        wrapper.eq(Item::getState, State.SHELVE);
        List<Item> itemList = itemMapper.selectList(wrapper);
        if (itemList.size() != ids.size()) {
            log.info("存在已下架的商品 {}", ids);
            throw new BusinessException(ITEM_DOWN);
        }
        return itemList.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
    }

    @Override
    public void updateSaleNum(Map<Long, Integer> itemNumMap) {
        for (Map.Entry<Long, Integer> entry : itemNumMap.entrySet()) {
            itemMapper.updateSaleNum(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void updateSaleNum(Long id, Integer num) {
        itemMapper.updateSaleNum(id, num);
    }

    @Override
    public void updateSaleNum(List<String> orderNoList) {
        orderNoList.forEach(itemMapper::updateSaleNumByOrderNo);
    }

    @Override
    public List<ItemVO> getPriorityItem(Long shopId) {
        int max = sysConfigApi.getInt(ConfigConstant.STORE_ITEM_MAX_RECOMMEND, 10);
        return itemMapper.getPriorityItem(shopId, max);
    }

    @Override
    public List<ItemVO> getRecommend() {
        int max = sysConfigApi.getInt(ConfigConstant.ITEM_MAX_RECOMMEND, 10);
        return itemMapper.getRecommendItem(max);
    }

    @Override
    public void setRecommend(Long id) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getRecommend, true);
        itemMapper.update(null, wrapper);
    }

    @Override
    public List<ItemVO> getByPage(ItemQueryDTO dto) {
        Page<ItemVO> voPage = itemMapper.getByPage(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public List<ItemVO> getCouponScopeByPage(ItemCouponQueryDTO dto) {
        Coupon coupon = couponService.selectByIdRequired(dto.getCouponId());
        // 增加过滤条件,提高查询效率
        dto.setStoreId(coupon.getStoreId());
        dto.setUseScope(coupon.getUseScope());
        return itemMapper.getCouponScopeByPage(dto.createPage(false), dto).getRecords();
    }

    @Override
    public TotalExpressVO calcExpressFee(List<ExpressFeeCalcDTO> dtoList) {
        List<StoreExpressVO> storeList = new ArrayList<>();
        int totalFee = 0;
        for (ExpressFeeCalcDTO dto : dtoList) {
            Integer expressFee = this.calcStoreExpressFee(dto);
            storeList.add(new StoreExpressVO(dto.getStoreId(), expressFee));
            totalFee += expressFee;
        }
        TotalExpressVO vo = new TotalExpressVO();
        vo.setTotalFee(totalFee);
        vo.setStoreList(storeList);
        return vo;
    }

    /**
     * 计算单店铺快递费用
     *
     * @param dto 一个店铺内下单的商品信息
     * @return 费用 分:
     */
    @Override
    public Integer calcStoreExpressFee(ExpressFeeCalcDTO dto) {
        List<Long> itemIds = dto.getOrderList().stream().map(ItemCalcDTO::getItemId).collect(Collectors.toList());
        List<ItemExpressVO> expressList = expressTemplateService.getExpressList(itemIds, dto.getStoreId());
        // 商品没有查询到快递信息,默认都是免邮
        if (CollUtil.isEmpty(expressList)) {
            return 0;
        }
        // 保存映射关系,减少后面数据库访问次数
        Map<Long, ItemExpressVO> expressMap = expressList.stream().collect(Collectors.toMap(ItemExpressVO::getItemId, Function.identity()));
        dto.getOrderList().forEach(itemCalcDTO -> {
            ItemExpressVO vo = expressMap.get(itemCalcDTO.getItemId());
            if (vo != null) {
                itemCalcDTO.setExpressId(vo.getExpressId());
                itemCalcDTO.setChargeMode(vo.getChargeMode());
            }
        });
        return itemExpressRegionService.calcFee(dto);
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO storeScore = orderEvaluationService.getStoreScore(vo.getStoreId());
        if (storeScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示零售店铺评分 [{}]", vo.getStoreId());
            return;
        }

        itemStoreMapper.updateScore(vo.getStoreId(), DecimalUtil.calcAvgScore(storeScore.getTotalScore(), storeScore.getNum()));

        AvgScoreVO productScore = orderEvaluationService.getProductScore(vo.getProductId());
        if (productScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示零售商品评分 [{}]", vo.getProductId());
            return;
        }
        itemMapper.updateScore(vo.getProductId(), DecimalUtil.calcAvgScore(productScore.getTotalScore(), productScore.getNum()));
    }

    @Override
    public ItemDetailVO detailById(Long id) {
        ItemDetailVO detail = itemMapper.detailById(id);
        if (detail == null) {
            log.error("该零售商品已删除啦 [{}]", id);
            throw new BusinessException(ITEM_DOWN);
        }
        ApplauseRateVO vo = orderEvaluationService.calcApplauseRate(id);
        // 最终上架状态必须个人和平台同时上架
        detail.setItemState((detail.getState() == State.SHELVE) ? 1 : 0);
        detail.setCommentNum(vo.getCommentNum());
        detail.setRate(vo.getRate());

        List<ItemSku> skuList = itemSkuService.getByItemId(id);
        if (Boolean.TRUE.equals(detail.getMultiSpec())) {
            detail.setSpecList(this.getSpecList(id));
            detail.setSkuList(DataUtil.copy(skuList, ItemSkuVO.class));
        } else {
            detail.setSkuList(Lists.newArrayList(DataUtil.copy(skuList.get(0), ItemSkuVO.class)));
        }
        this.setGroupBooking(detail);
        // 是否添加收藏
        detail.setCollect(memberCollectService.checkCollect(id, CollectType.ITEM));
        return detail;
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getState, State.UN_SHELVE);
        wrapper.eq(Item::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(Item::getDeleted, true);
        itemMapper.update(null, wrapper);
    }


    /**
     * 设置拼团信息
     *
     * @param detail 商品详情
     */
    private void setGroupBooking(ItemDetailVO detail) {
        if (detail.getBookingId() != null) {
            log.info("拼团商品,开始组装拼团价格信息 [{}] [{}]", detail.getId(), detail.getBookingId());
            GroupBooking booking = groupBookingMapper.selectById(detail.getBookingId());
            if (booking == null) {
                log.error("该拼团订单已删除啦 [{}]", detail.getBookingId());
                return;
            }
            if (booking.getStartTime().isAfter(LocalDateTime.now()) || booking.getEndTime().isBefore(LocalDateTime.now())) {
                log.error("该拼团不在有效期 [{}]", detail.getBookingId());
                return;
            }
            List<SkuRequest> skuList = jsonService.fromJsonList(booking.getSkuValue(), SkuRequest.class);
            Map<Long, SkuRequest> skuMap = skuList.stream().collect(Collectors.toMap(SkuRequest::getSkuId, Function.identity()));
            detail.getSkuList().forEach(vo -> {
                SkuRequest request = skuMap.get(vo.getId());
                if (request != null && vo.getSalePrice().equals(request.getSalePrice()) && request.getGroupPrice() != null) {
                    detail.setGroupBooking(true);
                    vo.setGroupPrice(request.getGroupPrice());
                } else {
                    vo.setGroupPrice(vo.getSalePrice());
                }
            });
        }
    }

    /**
     * 查询多规格商品的规格信息
     *
     * @param itemId id
     * @return 规格信息 按规格名分类
     */
    private List<ItemGroupSpecVO> getSpecList(Long itemId) {
        List<ItemSpec> specList = itemSpecService.getByItemId(itemId);
        Map<String, List<ItemSpec>> specMap = specList.stream().collect(Collectors.groupingBy(ItemSpec::getSpecName,
                Collectors.collectingAndThen(Collectors.toList(), specs -> specs.stream().sorted(Comparator.comparing(ItemSpec::getSort)).collect(Collectors.toList()))));
        List<ItemGroupSpecVO> voList = new ArrayList<>();
        for (Map.Entry<String, List<ItemSpec>> entry : specMap.entrySet()) {
            ItemGroupSpecVO vo = new ItemGroupSpecVO();
            vo.setSpecName(entry.getKey());
            vo.setValueList(DataUtil.copy(entry.getValue(), ItemSpecVO.class));
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 校验规格信息合法性
     *
     * @param multiSpec 是否为多规格
     * @param specList  规格信息
     */
    private void checkSpec(Boolean multiSpec, List<ItemSpecRequest> specList) {
        if (Boolean.TRUE.equals(multiSpec)) {
            BeanValidator.validateList(specList);
            this.redoSpecValue(specList);
        }
    }

    /**
     * 同一家店铺 商品名称重复校验
     *
     * @param itemName 商品名称
     * @param id       商品id
     * @param storeId  店铺id
     */
    private void titleRedo(String itemName, Long id, Long storeId) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Item::getTitle, itemName);
        wrapper.ne(id != null, Item::getId, id);
        wrapper.eq(Item::getStoreId, storeId);
        Long count = itemMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("零售商品名称重复 [{}] [{}] [{}]", itemName, id, storeId);
            throw new BusinessException(ErrorCode.ITEM_TITLE_REDO);
        }
    }

    /**
     * 针对物流选择计重模板,重量必填
     *
     * @param expressId 快递模板id
     * @param skuList   skuList
     */
    private void checkExpress(Long expressId, List<ItemSkuRequest> skuList) {
        if (expressId != null) {
            ExpressTemplate selected = expressTemplateService.selectByIdRequired(expressId);
            if (selected.getChargeMode() == ChargeMode.WEIGHT.getValue()) {
                boolean anyMatch = skuList.stream().anyMatch(itemSkuRequest -> itemSkuRequest.getWeight() == null);
                if (anyMatch) {
                    throw new BusinessException(EXPRESS_WEIGHT);
                }
            }
        }
    }

    /**
     * 规格值重复校验
     *
     * @param specList 规格信息
     */
    private void redoSpecValue(List<ItemSpecRequest> specList) {
        if (specList.size() == 1) {
            return;
        }
        long size = specList.stream().map(ItemSpecRequest::getSpecValue).distinct().count();
        if (specList.size() != size) {
            throw new BusinessException(ErrorCode.SKU_TITLE_REDO);
        }
    }

    /**
     * 设置商品的最大值和最小值
     *
     * @param item    商品信息
     * @param skuList 商品sku的信息
     */
    private void setMinMaxPrice(Item item, List<ItemSkuRequest> skuList) {
        if (CollUtil.isNotEmpty(skuList)) {
            OptionalInt max = skuList.stream().mapToInt(ItemSkuRequest::getSalePrice).max();
            if (max.isPresent()) {
                item.setMaxPrice(max.getAsInt());
            }
            OptionalInt min = skuList.stream().mapToInt(ItemSkuRequest::getSalePrice).min();
            if (min.isPresent()) {
                item.setMinPrice(min.getAsInt());
            }
        }
    }
}
