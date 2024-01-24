package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.RoleType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mapper.RestaurantMapper;
import com.eghm.mapper.VoucherMapper;
import com.eghm.model.Merchant;
import com.eghm.model.Restaurant;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.RestaurantService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.restaurant.RestaurantDetailVO;
import com.eghm.vo.business.restaurant.RestaurantResponse;
import com.eghm.vo.business.restaurant.RestaurantVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@Service("restaurantService")
@AllArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService, MerchantInitService {

    private final RestaurantMapper restaurantMapper;

    private final SysAreaService sysAreaService;

    private final CommonService commonService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    private final VoucherMapper voucherMapper;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<Restaurant> getByPage(RestaurantQueryRequest request) {
        LambdaQueryWrapper<Restaurant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Restaurant::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Restaurant::getTitle, request.getQueryName());
        wrapper.eq(request.getMerchantId() != null, Restaurant::getMerchantId, request.getMerchantId());
        return restaurantMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public List<RestaurantResponse> getList(RestaurantQueryRequest request) {
        Page<RestaurantResponse> responsePage = restaurantMapper.listPage(request.createNullPage(), request);
        return responsePage.getRecords();
    }

    @Override
    public void create(RestaurantAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        Restaurant restaurant = DataUtil.copy(request, Restaurant.class);
        restaurant.setState(State.UN_SHELVE);
        restaurant.setMerchantId(SecurityHolder.getMerchantId());
        restaurantMapper.insert(restaurant);
    }

    @Override
    public void update(RestaurantEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        Restaurant required = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(required.getMerchantId());

        Restaurant restaurant = DataUtil.copy(request, Restaurant.class);
        restaurantMapper.updateById(restaurant);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getState, state);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Restaurant::getMerchantId, merchantId);
        restaurantMapper.update(null, wrapper);
    }

    @Override
    public Restaurant selectByIdRequired(Long id) {
        Restaurant restaurant = restaurantMapper.selectById(id);
        if (restaurant == null) {
            log.info("餐饮商家信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.RESTAURANT_NOT_FOUND);
        }
        return restaurant;
    }

    @Override
    public Restaurant selectByIdShelve(Long id) {
        Restaurant restaurant = this.selectByIdRequired(id);
        if (restaurant.getState() != State.SHELVE) {
            log.info("餐饮商家信息已下架 [{}] [{}]", id, restaurant.getState());
            throw new BusinessException(ErrorCode.RESTAURANT_DOWN);
        }
        return restaurant;
    }

    @Override
    public List<RestaurantVO> getByPage(RestaurantQueryDTO dto) {
        if (Boolean.TRUE.equals(dto.getSortByDistance()) && (dto.getLongitude() == null || dto.getLatitude() == null)) {
            log.info("餐饮列表未获取到用户经纬度, 无法进行距离排序 [{}] [{}]", dto.getLongitude(), dto.getLatitude());
            throw new BusinessException(ErrorCode.POSITION_NO);
        }
        return restaurantMapper.getByPage(dto.createPage(false), dto).getRecords();
    }

    @Override
    public RestaurantDetailVO detailById(Long id) {
        Restaurant restaurant = this.selectByIdShelve(id);
        RestaurantDetailVO vo = DataUtil.copy(restaurant, RestaurantDetailVO.class);
        vo.setDetailAddress(sysAreaService.parseArea(restaurant.getCityId(), restaurant.getCountyId()) + restaurant.getDetailAddress());
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.VOUCHER_STORE));
        return vo;
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getState, State.UN_SHELVE);
        wrapper.set(Restaurant::getDeleted, true);
        wrapper.eq(Restaurant::getMerchantId, SecurityHolder.getMerchantId());
        restaurantMapper.update(null, wrapper);
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO storeScore = orderEvaluationMapper.getStoreScore(vo.getStoreId());
        if (storeScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示餐饮商家评分 [{}]", vo.getStoreId());
            return;
        }
        restaurantMapper.updateScore(vo.getStoreId(), DecimalUtil.calcAvgScore(storeScore.getTotalScore(), storeScore.getNum()));
        AvgScoreVO productScore = orderEvaluationMapper.getProductScore(vo.getProductId());
        if (productScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示餐饮券商品评分 [{}]", vo.getProductId());
            return;
        }
        voucherMapper.updateScore(vo.getProductId(), DecimalUtil.calcAvgScore(productScore.getTotalScore(), productScore.getNum()));
    }

    /**
     * 校验餐饮商家名称是否重复
     *
     * @param title 名称
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<Restaurant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Restaurant::getTitle, title);
        wrapper.ne(id != null, Restaurant::getId, id);
        Long count = restaurantMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("餐饮商家名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.RESTAURANT_TITLE_REDO);
        }
    }

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.RESTAURANT);
    }

    @Override
    public void init(Merchant merchant) {
        Restaurant restaurant = new Restaurant();
        restaurant.setMerchantId(merchant.getId());
        restaurantMapper.insert(restaurant);
    }
}
