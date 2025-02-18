package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.statistics.CollectRequest;
import com.eghm.enums.CollectType;
import com.eghm.enums.SelectType;
import com.eghm.mapper.*;
import com.eghm.model.MemberCollect;
import com.eghm.service.business.MemberCollectService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.homestay.HomestayVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.TravelVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;

/**
 * <p>
 * 会员收藏记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Slf4j
@AllArgsConstructor
@Service("memberCollectService")
public class MemberCollectServiceImpl implements MemberCollectService {

    private final ItemMapper itemMapper;

    private final LineMapper lineMapper;

    private final NewsMapper newsMapper;

    private final CacheService cacheService;

    private final ScenicMapper scenicMapper;

    private final HomestayMapper homestayMapper;

    private final ItemStoreMapper itemStoreMapper;

    private final RestaurantMapper restaurantMapper;

    private final TravelAgencyMapper travelAgencyMapper;

    private final MemberCollectMapper memberCollectMapper;

    @Override
    public List<MemberCollectVO> getByPage(CollectQueryDTO query) {
        Page<MemberCollectVO> byPage = memberCollectMapper.getByPage(query.createPage(false), query);
        if (CollUtil.isNotEmpty(byPage.getRecords())) {
            Map<CollectType, List<Long>> collectMap = byPage.getRecords().stream().collect(Collectors.groupingBy(MemberCollectVO::getCollectType, Collectors.mapping(MemberCollectVO::getCollectId, Collectors.toList())));
            Map<Long, ScenicVO> scenicMap = this.getScenicMap(collectMap.get(CollectType.SCENIC));
            Map<Long, HomestayVO> homestayMap = this.getHomestayMap(collectMap.get(CollectType.HOMESTAY));
            Map<Long, ItemStoreVO> itemStoreMap = this.getItemStoreMap(collectMap.get(CollectType.ITEM_STORE));
            Map<Long, ItemVO> itemMap = this.getItemMap(collectMap.get(CollectType.ITEM));
            Map<Long, LineVO> lineMap = this.getLineMap(collectMap.get(CollectType.LINE));
            Map<Long, TravelVO> travelMap = this.getTravelMap(collectMap.get(CollectType.TRAVEL_AGENCY));
            Map<Long, NewsVO> newsMap = this.getNewsMap(collectMap.get(CollectType.NEWS));
            Map<Long, RestaurantVO> restaurantMap = this.getRestaurantMap(collectMap.get(CollectType.VOUCHER_STORE));
            Iterator<MemberCollectVO> iterator = byPage.getRecords().iterator();
            while (iterator.hasNext()) {
                MemberCollectVO vo = iterator.next();
                switch (vo.getCollectType()) {
                    case SCENIC:
                        vo.setScenic(scenicMap.get(vo.getCollectId()));
                        break;
                    case HOMESTAY:
                        vo.setHomestay(homestayMap.get(vo.getCollectId()));
                        break;
                    case ITEM_STORE:
                        vo.setItemStore(itemStoreMap.get(vo.getCollectId()));
                        break;
                    case ITEM:
                        vo.setItem(itemMap.get(vo.getCollectId()));
                        break;
                    case LINE:
                        vo.setLine(lineMap.get(vo.getCollectId()));
                        break;
                    case TRAVEL_AGENCY:
                        vo.setTravelAgency(travelMap.get(vo.getCollectId()));
                        break;
                    case NEWS:
                        vo.setNews(newsMap.get(vo.getCollectId()));
                        break;
                    case VOUCHER_STORE:
                        vo.setRestaurant(restaurantMap.get(vo.getCollectId()));
                        break;
                    default:
                        // 表示该收藏类型和id不匹配
                        iterator.remove();
                        break;
                }
            }
        }
        return byPage.getRecords();
    }

    @Override
    public void collect(Long collectId, CollectType collectType) {
        if (!this.isLegal(collectId, collectType)) {
            log.warn("非法收藏对象,不做任何操作 [{}] [{}]", collectId, collectType);
            return;
        }
        Long memberId = ApiHolder.getMemberId();
        MemberCollect collect = this.getMemberCollect(memberId, collectId, collectType);
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        if (collect != null) {
            if (collect.getState() == 1) {
                collect.setState(0);
                cacheService.deleteHashKey(key, String.valueOf(memberId));
            } else {
                collect.setState(1);
                cacheService.setHashValue(key, String.valueOf(memberId), CacheConstant.PLACE_HOLDER);
            }
            memberCollectMapper.updateById(collect);
        } else {
            collect = new MemberCollect();
            collect.setMemberId(memberId);
            collect.setCollectId(collectId);
            collect.setCollectType(collectType);
            collect.setCreateDate(LocalDate.now());
            memberCollectMapper.insert(collect);
            cacheService.setHashValue(key, String.valueOf(memberId), CacheConstant.PLACE_HOLDER);
        }
    }

    @Override
    public boolean checkCollect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
    }

    @Override
    public List<CollectStatisticsVO> dayCollect(CollectRequest request) {
        List<CollectStatisticsVO> voList = memberCollectMapper.dayCollect(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        } else {
            Map<LocalDate, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateDate, Function.identity()));
            return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        }
    }

    /**
     * 判断收藏对象是否合法
     *
     * @param collectId 收藏对象
     * @param collectType 收藏对象类型
     * @return true:合法 false:不合法
     */
    private boolean isLegal(Long collectId, CollectType collectType) {
        switch (collectType) {
            case SCENIC:
                return scenicMapper.selectById(collectId) != null;
            case HOMESTAY:
                return homestayMapper.selectById(collectId) != null;
            case ITEM_STORE:
                return itemStoreMapper.selectById(collectId) != null;
            case ITEM:
                return itemMapper.selectById(collectId) != null;
            case LINE:
                return lineMapper.selectById(collectId) != null;
            case TRAVEL_AGENCY:
                return travelAgencyMapper.selectById(collectId) != null;
            case NEWS:
                return newsMapper.selectById(collectId) != null;
            case VOUCHER_STORE:
                return restaurantMapper.selectById(collectId) != null;
            default:
                return false;
        }
    }

    /**
     * 根据景区id查询景区信息
     *
     * @param scenicIds 景区id
     * @return 景区信息
     */
    private Map<Long, ScenicVO> getScenicMap(List<Long> scenicIds) {
        if (CollUtil.isEmpty(scenicIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<ScenicVO> voList = scenicMapper.getList(scenicIds);
        return voList.stream().collect(Collectors.toMap(ScenicVO::getId, Function.identity()));
    }

    /**
     * 根据民宿id查询民宿信息
     *
     * @param homestayIds 民宿id
     * @return 民宿信息
     */
    private Map<Long, HomestayVO> getHomestayMap(List<Long> homestayIds) {
        if (CollUtil.isEmpty(homestayIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<HomestayVO> voList = homestayMapper.getList(homestayIds);
        return voList.stream().collect(Collectors.toMap(HomestayVO::getId, Function.identity()));
    }

    /**
     * 根据零售店铺id查询店铺信息
     *
     * @param storeIds 店铺id
     * @return 店铺信息
     */
    private Map<Long, ItemStoreVO> getItemStoreMap(List<Long> storeIds) {
        if (CollUtil.isEmpty(storeIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<ItemStoreVO> voList = itemStoreMapper.getList(storeIds);
        return voList.stream().collect(Collectors.toMap(ItemStoreVO::getId, Function.identity()));
    }

    /**
     * 查询零售商品信息
     *
     * @param itemIds 商品id
     * @return 商品信息
     */
    private Map<Long, ItemVO> getItemMap(List<Long> itemIds) {
        if (CollUtil.isEmpty(itemIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<ItemVO> voList = itemMapper.getList(itemIds);
        return voList.stream().collect(Collectors.toMap(ItemVO::getId, Function.identity()));
    }

    /**
     * 查询线路商品信息
     *
     * @param lineIds ids
     * @return 线路信息
     */
    private Map<Long, LineVO> getLineMap(List<Long> lineIds) {
        if (CollUtil.isEmpty(lineIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<LineVO> voList = lineMapper.getList(lineIds);
        return voList.stream().collect(Collectors.toMap(LineVO::getId, Function.identity()));
    }

    /**
     * 查询餐饮店信息
     *
     * @param restaurantIds ids
     * @return 餐饮店信息
     */
    private Map<Long, RestaurantVO> getRestaurantMap(List<Long> restaurantIds) {
        if (CollUtil.isEmpty(restaurantIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<RestaurantVO> voList = restaurantMapper.getList(restaurantIds);
        return voList.stream().collect(Collectors.toMap(RestaurantVO::getId, Function.identity()));
    }

    /**
     * 查询资讯信息
     *
     * @param newsIds id
     * @return 资讯信息
     */
    private Map<Long, NewsVO> getNewsMap(List<Long> newsIds) {
        if (CollUtil.isEmpty(newsIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<NewsVO> voList = newsMapper.getList(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, Function.identity()));
    }

    /**
     * 查询旅行社信息
     *
     * @param travelIds id
     * @return 旅行社信息
     */
    private Map<Long, TravelVO> getTravelMap(List<Long> travelIds) {
        if (CollUtil.isEmpty(travelIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<TravelVO> voList = travelAgencyMapper.getList(travelIds);
        return voList.stream().collect(Collectors.toMap(TravelVO::getId, Function.identity()));
    }

    /**
     * 获取用户收藏记录
     *
     * @param memberId    会员id
     * @param collectId   收藏id
     * @param collectType 收藏类型
     * @return 收藏记录
     */
    private MemberCollect getMemberCollect(Long memberId, Long collectId, CollectType collectType) {
        LambdaQueryWrapper<MemberCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberCollect::getCollectId, collectId);
        wrapper.eq(MemberCollect::getMemberId, memberId);
        wrapper.eq(MemberCollect::getCollectType, collectType);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberCollectMapper.selectOne(wrapper);
    }

}
