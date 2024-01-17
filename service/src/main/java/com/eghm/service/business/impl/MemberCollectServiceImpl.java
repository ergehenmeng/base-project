package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ref.CollectType;
import com.eghm.mapper.*;
import com.eghm.model.MemberCollect;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.cache.CacheService;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.homestay.HomestayVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.TravelAgencyVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constant.CacheConstant.MEMBER_COLLECT;

/**
 * <p>
 * 会员收藏记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@AllArgsConstructor
@Service("memberCollectService")
public class MemberCollectServiceImpl implements MemberCollectService {

    private final CacheService cacheService;

    private final MemberCollectMapper memberCollectMapper;

    private final ScenicMapper scenicMapper;

    private final HomestayMapper homestayMapper;

    private final ItemStoreMapper itemStoreMapper;

    private final ItemMapper itemMapper;

    private final LineMapper lineMapper;

    private final RestaurantMapper restaurantMapper;

    private final NewsMapper newsMapper;

    private final TravelAgencyMapper travelAgencyMapper;

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
            Map<Long, TravelAgencyVO> travelMap = this.getTravelMap(collectMap.get(CollectType.TRAVEL_AGENCY));
            Map<Long, NewsVO> newsMap = this.getNewsMap(collectMap.get(CollectType.NEWS));
            Map<Long, RestaurantVO> restaurantMap = this.getRestaurantMap(collectMap.get(CollectType.VOUCHER_STORE));
            for (MemberCollectVO vo : byPage.getRecords()) {
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
                        break;
                }
            }
        }
        return byPage.getRecords();
    }

    @Override
    public void collect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.getMemberId();
        MemberCollect collect = this.getMemberCollect(memberId, collectId, collectType);
        String key = String.format(MEMBER_COLLECT, collectType.getName().toLowerCase(), collectId);
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
        String key = String.format(MEMBER_COLLECT, collectType.getName().toLowerCase(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
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
    private Map<Long, TravelAgencyVO> getTravelMap(List<Long> travelIds) {
        if (CollUtil.isEmpty(travelIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<TravelAgencyVO> voList = travelAgencyMapper.getList(travelIds);
        return voList.stream().collect(Collectors.toMap(TravelAgencyVO::getId, Function.identity()));
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
