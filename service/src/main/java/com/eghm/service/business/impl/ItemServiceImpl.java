package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.JsonService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.coupon.product.ItemCouponQueryDTO;
import com.eghm.dto.business.item.*;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.dto.ext.DiscountJson;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChargeMode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.SpecLevel;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.GroupBookingMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.mapper.ItemStoreMapper;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.evaluation.ApplauseRateVO;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.item.*;
import com.eghm.vo.business.item.express.ExpressTemplateVO;
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

import static com.eghm.constant.CommonConstant.ITEM_TAG_STEP;
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

    private final JsonService jsonService;

    private final SysConfigApi sysConfigApi;

    private final CouponService couponService;

    private final CommonService commonService;

    private final ItemSkuService itemSkuService;

    private final ItemStoreMapper itemStoreMapper;

    private final ItemSpecService itemSpecService;

    private final GroupBookingMapper groupBookingMapper;

    private final MemberCollectService memberCollectService;

    private final ExpressTemplateService expressTemplateService;

    private final OrderEvaluationService orderEvaluationService;

    private final LimitPurchaseItemService limitPurchaseItemService;

    private final ExpressTemplateRegionService expressTemplateRegionService;

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
        Long merchantId = SecurityHolder.getMerchantId();
        this.checkStore(merchantId, request.getStoreId());
        this.titleRedo(request.getTitle(), null, request.getStoreId());
        this.checkExpress(request.getExpressId(), request.getSkuList());

        Item item = DataUtil.copy(request, Item.class);
        item.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        item.setMerchantId(merchantId);
        item.setCreateDate(LocalDate.now());
        item.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
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
        this.checkStore(SecurityHolder.getMerchantId(), request.getStoreId());
        this.titleRedo(request.getTitle(), request.getId(), request.getStoreId());
        this.checkExpress(request.getExpressId(), request.getSkuList());
        Item select = this.selectByIdRequired(request.getId());
        this.checkMultiSpec(select.getMultiSpec(), request.getMultiSpec());
        commonService.checkIllegal(select.getMerchantId());

        Item item = DataUtil.copy(request, Item.class);
        item.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        if (select.getBookingId() == null) {
            Map<String, Long> specMap = itemSpecService.update(item, request.getSpecList());
            itemSkuService.update(item, specMap, request.getSkuList());
        } else {
            log.info("该商品是拼团商品,请先删除拼团活动后再编辑规格信息 [{}] [{}]", select.getId(), select.getBookingId());
        }
        // 因为可能修改了虚拟库存，需要重新计算总销量
        Integer totalNum = itemSkuService.calcTotalNum(item.getId());
        item.setTotalNum(totalNum);
        itemMapper.updateById(item);
    }

    @Override
    public ItemDetailResponse getDetailById(Long itemId) {
        Item item = this.selectByIdRequired(itemId);
        ItemDetailResponse response = DataUtil.copy(item, ItemDetailResponse.class);
        response.setTagList(this.parseTagId(item.getTagId()));
        List<ItemSku> skuList = itemSkuService.getSkuList(itemId);
        response.setSkuList(DataUtil.copy(skuList, ItemSkuResponse.class));
        // 多规格才会保存规格配置信息
        if (Boolean.TRUE.equals(item.getMultiSpec())) {
            List<ItemSpec> spec = itemSpecService.getByItemId(itemId);
            List<ItemSpecValueResponse> specList = DataUtil.copy(spec, ItemSpecValueResponse.class);
            List<ItemSpecResponse> responseList = this.groupBySpecName(specList);
            response.setSpecList(responseList);
            this.setMergeColspan(response);
        }
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
    public void checkIllegal(List<Long> itemIds, Long merchantId) {
        if (CollUtil.isEmpty(itemIds)) {
            return;
        }
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Item::getId, itemIds);
        wrapper.ne(Item::getMerchantId, merchantId);
        long count = itemMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("非法零售数据操作 [{}] [{}]", merchantId, itemIds);
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Item::getMerchantId, merchantId);
        wrapper.set(Item::getState, state);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void updateGroupBooking(Long id, Long bookingId) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getBookingId, bookingId);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void updateLimitPurchase(List<Long> itemIds, Long limitId) {
        Long merchantId = SecurityHolder.getMerchantId();
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getLimitId, limitId);
        wrapper.eq(Item::getMerchantId, merchantId);
        wrapper.set(Item::getLimitId, null);
        itemMapper.update(null, wrapper);

        LambdaUpdateWrapper<Item> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.in(Item::getId, itemIds);
        updateWrapper.eq(Item::getMerchantId, merchantId);
        updateWrapper.isNull(Item::getLimitId);
        updateWrapper.set(Item::getLimitId, limitId);
        int update = itemMapper.update(null, updateWrapper);
        if (update != itemIds.size()) {
            log.error("限时购活动更新的商品可能不属当前商户 [{}] [{}] [{}]", merchantId, limitId, itemIds);
            throw new BusinessException(ErrorCode.LIMIT_ITEM_NULL);
        }
    }

    @Override
    public void releasePurchase(Long limitId) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getLimitId, limitId);
        wrapper.set(Item::getLimitId, null);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.eq(Item::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(Item::getSort, sortBy);
        itemMapper.update(null, wrapper);
    }

    @Override
    public Map<Long, Item> getByIdShelveMap(Set<Long> ids) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
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
    public void updateSaleNum(List<String> orderNoList) {
        itemMapper.updateSaleNum(orderNoList);
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
    public void setRecommend(Long id, boolean recommend) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getRecommend, recommend);
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
        List<ExpressTemplateVO> expressList = expressTemplateService.getExpressList(itemIds, dto.getStoreId());
        // 商品没有查询到快递信息,默认都是免邮
        if (CollUtil.isEmpty(expressList)) {
            return 0;
        }
        // 保存映射关系,减少后面数据库访问次数
        Map<Long, ExpressTemplateVO> expressMap = expressList.stream().collect(Collectors.toMap(ExpressTemplateVO::getItemId, Function.identity()));
        dto.getOrderList().forEach(itemCalcDTO -> {
            ExpressTemplateVO vo = expressMap.get(itemCalcDTO.getItemId());
            if (vo != null) {
                itemCalcDTO.setExpressId(vo.getExpressId());
                itemCalcDTO.setChargeMode(vo.getChargeMode());
            }
        });
        return expressTemplateRegionService.calcFee(dto);
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
        detail.setGoodRate(vo.getRate());

        List<ItemSkuVO> skuList = itemSkuService.getByItemId(id);
        detail.setSkuList(skuList);
        if (Boolean.TRUE.equals(detail.getMultiSpec())) {
            detail.setSpecList(this.getSpecList(id));
        }
        // 限时购商品设置限时购价格
        this.setLimitPurchase(detail);
        // 设置拼团信息价格
        this.setGroupBooking(detail);
        // 是否添加收藏
        detail.setCollect(memberCollectService.checkCollect(id, CollectType.ITEM));
        return detail;
    }

    @Override
    public ItemSkuDetailVO skuDetailById(Long id) {
        Item item = itemMapper.selectById(id);
        if (item == null) {
            log.error("该零售商品已删除啦 [{}]", id);
            throw new BusinessException(ITEM_DOWN);
        }
        ItemSkuDetailVO detail = DataUtil.copy(item, ItemSkuDetailVO.class);
        List<ItemSkuVO> skuList = itemSkuService.getByItemId(id);
        detail.setSkuList(skuList);
        if (Boolean.TRUE.equals(item.getMultiSpec())) {
            detail.setSpecList(this.getSpecList(id));
        }
        return detail;
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.eq(Item::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(Item::getState, State.UN_SHELVE);
        wrapper.set(Item::getDeleted, true);
        itemMapper.update(null, wrapper);
    }

    @Override
    public List<ActivityItemResponse> getActivityList(Long merchantId, Long activityId) {
        List<ActivityItemResponse> activityList = itemMapper.getActivityList(merchantId, activityId);
        for (ActivityItemResponse response : activityList) {
            List<BaseSkuResponse> skuList = response.getSkuList();
            for (BaseSkuResponse sku : skuList) {
                sku.setItemId(response.getId());
                sku.setTitle(response.getTitle());
                sku.setSkuSize(skuList.size());
                if (StrUtil.isBlank(sku.getSkuPic())) {
                    if (StrUtil.isBlank(sku.getSpecPic())) {
                        sku.setSkuPic(response.getCoverUrl());
                    } else {
                        sku.setSkuPic(sku.getSpecPic());
                    }
                }
                if (StrUtil.isBlank(sku.getSecondSpecValue())) {
                    sku.setSpecValue(sku.getPrimarySpecValue());
                } else {
                    sku.setSpecValue(sku.getPrimarySpecValue() + "/" + sku.getSecondSpecValue());
                }
            }
        }
        return activityList;
    }

    @Override
    public void setDiscountSkuPrice(List<ItemSkuVO> skuList, String jsonValue) {
        List<DiscountJson> groupSkuList = jsonService.fromJsonList(jsonValue, DiscountJson.class);
        Map<Long, DiscountJson> skuMap = groupSkuList.stream().collect(Collectors.toMap(DiscountJson::getSkuId, Function.identity()));
        skuList.forEach(vo -> {
            DiscountJson request = skuMap.get(vo.getId());
            if (request != null && vo.getSalePrice().equals(request.getSalePrice()) && request.getDiscountPrice() != null) {
                vo.setDiscountPrice(request.getDiscountPrice());
            } else {
                vo.setDiscountPrice(vo.getSalePrice());
            }
        });
    }

    @Override
    public Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request) {
        return itemMapper.getProductPage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public boolean containHot(Collection<Long> itemIds) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Item::getId, itemIds);
        wrapper.eq(Item::getHotSell, true);
        return itemMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Item::getState, State.FORCE_UN_SHELVE);
        wrapper.eq(Item::getMerchantId, merchantId);
        itemMapper.update(null, wrapper);
    }

    @Override
    public void addStock(ItemAddStockRequest request) {
        Item item = this.selectByIdRequired(request.getItemId());
        commonService.checkIllegal(item.getMerchantId());
        itemSkuService.addStock(request.getSkuList(), request.getItemId());
    }

    /**
     * 针对多多规格商品时要合并单元格,此方法为计算合并的单元格数量,方便前端展示
     *
     * @param response 返回给前端的信息
     */
    private void setMergeColspan(ItemDetailResponse response) {
        List<ItemSpecResponse> specList = response.getSpecList();
        if (specList.size() > 1) {
            // 两级规格才合并
            ItemSpecResponse itemSpec = specList.get(1);
            for (ItemSkuResponse skuResponse : response.getSkuList()) {
                if (itemSpec.getSpecName().equals(skuResponse.getPrimarySpecValue())) {
                    skuResponse.setSecondSize(itemSpec.getValueList().size());
                }
            }
        }
    }

    /**
     * 格式化标签,方便前端展示
     *
     * @param tagId 标签ID
     * @return 列表
     */
    private List<String> parseTagId(String tagId) {
        if (StrUtil.isBlank(tagId)) {
            return Collections.emptyList();
        }
        int length = tagId.length();
        int step = ITEM_TAG_STEP.length();
        List<String> tagList = Lists.newArrayListWithExpectedSize(5);
        for (int i = step; i <= length; i += step) {
            tagList.add(tagId.substring(0, i));
        }
        return tagList;
    }

    /**
     * 根据规格名进行分组
     *
     * @param specList 规格信息
     * @return 分组后的规格信息
     */
    private List<ItemSpecResponse> groupBySpecName(List<ItemSpecValueResponse> specList) {
        LinkedHashMap<SpecLevel, List<ItemSpecValueResponse>> specMap = specList.stream().collect(Collectors.groupingBy(ItemSpecValueResponse::getLevel, LinkedHashMap::new, Collectors.toList()));
        List<ItemSpecResponse> result = new ArrayList<>();
        for (SpecLevel value : SpecLevel.values()) {
            List<ItemSpecValueResponse> specValue = specMap.get(value);
            if (CollUtil.isNotEmpty(specValue)) {
                ItemSpecResponse spec = new ItemSpecResponse();
                spec.setSpecName(specValue.get(0).getSpecName());
                spec.setLevel(value.getValue());
                spec.setValueList(specValue);
                result.add(spec);
            }
        }
        return result;
    }

    /**
     * 设置限时购信息
     *
     * @param detail 商品详情
     */
    private void setLimitPurchase(ItemDetailVO detail) {
        if (detail.getLimitId() != null) {
            log.info("限时购商品,开始组装限时购价格信息 [{}] [{}]", detail.getId(), detail.getLimitId());
            LimitPurchaseItem purchaseItem = limitPurchaseItemService.getLimitItem(detail.getLimitId(), detail.getId());
            if (purchaseItem == null) {
                log.error("该限时购商品已删除 [{}] [{}]", detail.getLimitId(), detail.getId());
                return;
            }
            if (purchaseItem.getEndTime().isBefore(LocalDateTime.now())) {
                log.error("该限时购商品已过有效期 [{}] [{}]", detail.getLimitId(), purchaseItem.getEndTime());
                return;
            }
            if (purchaseItem.getAdvanceTime().isBefore(LocalDateTime.now())) {
                log.error("该限时购商品还没到开始时间 [{}] [{}]", detail.getLimitId(), purchaseItem.getAdvanceTime());
                return;
            }
            this.setDiscountSkuPrice(detail.getSkuList(), purchaseItem.getSkuValue());
            detail.setLimitPurchase(true);
            detail.setStartTime(purchaseItem.getStartTime());
            detail.setEndTime(purchaseItem.getEndTime());
            detail.setSystemTime(LocalDateTime.now());
        }
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
            detail.setGroupBooking(true);
            this.setDiscountSkuPrice(detail.getSkuList(), booking.getSkuValue());
        }
    }

    /**
     * 查询多规格商品的规格信息
     *
     * @param itemId id
     * @return 规格信息 按规格名分类
     */
    private List<ItemSpecVO> getSpecList(Long itemId) {
        List<ItemSpec> specList = itemSpecService.getByItemId(itemId);
        Map<String, List<ItemSpec>> specMap = specList.stream().collect(Collectors.groupingBy(ItemSpec::getSpecName,
                Collectors.collectingAndThen(Collectors.toList(), specs -> specs.stream().sorted(Comparator.comparing(ItemSpec::getSort)).collect(Collectors.toList()))));
        List<ItemSpecVO> voList = new ArrayList<>();
        for (Map.Entry<String, List<ItemSpec>> entry : specMap.entrySet()) {
            ItemSpecVO vo = new ItemSpecVO();
            vo.setSpecName(entry.getKey());
            vo.setValueList(DataUtil.copy(entry.getValue(), ItemSpecDetailVO.class));
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
     * 编辑时不允许修改规格类型
     *
     * @param multiSpec 原规格类型
     * @param request 新规格类型
     */
    private void checkMultiSpec(Boolean multiSpec, Boolean request) {
        if (!request.equals(multiSpec)) {
            throw new BusinessException(ErrorCode.ITEM_MULTI_SPEC_ERROR);
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
     * 校验店铺是否属于对应商户
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     */
    private void checkStore(Long merchantId, Long storeId) {
        LambdaQueryWrapper<ItemStore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemStore::getMerchantId, merchantId);
        wrapper.eq(ItemStore::getId, storeId);
        Long count = itemStoreMapper.selectCount(wrapper);
        if (count <= 0) {
            log.info("新增商品,店铺不存在 [{}] [{}]", merchantId, storeId);
            throw new BusinessException(ErrorCode.STORE_NOT_EXIST);
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
        specList.forEach(spec -> {
            long size = spec.getValueList().stream().map(ItemSpecRequest.SpecValue::getName).distinct().count();
            if (spec.getValueList().size() != size) {
                throw new BusinessException(ErrorCode.SKU_TITLE_REDO);
            }
        });
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
