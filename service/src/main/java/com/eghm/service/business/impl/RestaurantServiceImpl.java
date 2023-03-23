package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.RestaurantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.Restaurant;
import com.eghm.model.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.model.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.model.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.model.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.model.vo.business.restaurant.RestaurantListVO;
import com.eghm.model.vo.business.restaurant.RestaurantVO;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.RestaurantService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Service("restaurantService")
@AllArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService, MerchantInitService {

    private final RestaurantMapper restaurantMapper;

    private final SysAreaService sysAreaService;
    
    private final CommonService commonService;

    @Override
    public Page<Restaurant> getByPage(RestaurantQueryRequest request) {
        LambdaQueryWrapper<Restaurant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Restaurant::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Restaurant::getTitle, request.getQueryName());
        return restaurantMapper.selectPage(request.createPage(), wrapper);
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
        // 商户在进行注册时默认会初始化一条餐饮店(未激活状态), 更新时自动变更为激活后的状态,即:待上架
        if (required.getState() == State.INIT) {
            restaurant.setState(State.UN_SHELVE);
        }
        restaurantMapper.updateById(restaurant);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getState, state);
        restaurantMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getPlatformState, state);
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
        if (restaurant.getPlatformState() != PlatformState.SHELVE) {
            log.info("餐饮商家信息已下架 [{}] [{}]", id, restaurant.getPlatformState());
            throw new BusinessException(ErrorCode.RESTAURANT_DOWN);
        }
        return restaurant;
    }

    @Override
    public List<RestaurantListVO> getByPage(RestaurantQueryDTO dto) {
        if (Boolean.TRUE.equals(dto.getSortByDistance()) && (dto.getLongitude() == null || dto.getLatitude() == null)) {
            log.info("餐饮列表未获取到用户经纬度, 无法进行距离排序 [{}] [{}]", dto.getLongitude(), dto.getLatitude());
            throw new BusinessException(ErrorCode.POSITION_NO);
        }
        return restaurantMapper.getByPage(dto.createPage(false), dto).getRecords();
    }

    @Override
    public RestaurantVO detailById(Long id) {
        Restaurant restaurant = this.selectByIdShelve(id);
        RestaurantVO vo = DataUtil.copy(restaurant, RestaurantVO.class);
        vo.setDetailAddress(sysAreaService.parseArea(restaurant.getCityId(), restaurant.getCountyId()) + restaurant.getDetailAddress());
        return vo;
    }
    
    @Override
    public void deleteById(Long id) {
        restaurantMapper.deleteById(id);
    }
    
    /**
     * 校验餐饮商家名称是否重复
     * @param title 名称
     * @param id id 编辑时不能为空
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
        restaurant.setState(State.INIT);
        restaurantMapper.insert(restaurant);
    }
}
