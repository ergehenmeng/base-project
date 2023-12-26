package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.config.CouponAddRequest;
import com.eghm.dto.business.coupon.config.CouponEditRequest;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.CouponMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.Coupon;
import com.eghm.model.Item;
import com.eghm.service.business.CouponScopeService;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.coupon.CouponListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.ITEM_DOWN;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("couponService")
@AllArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final CouponMapper couponMapper;

    private final CouponScopeService couponScopeService;

    private final MemberCouponService memberCouponService;

    private final ItemMapper itemMapper;

    @Override
    public Page<Coupon> getByPage(CouponQueryRequest request) {
        LambdaQueryWrapper<Coupon> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Coupon::getTitle, request.getQueryName());
        if (request.getState() != null) {
            wrapper.gt( request.getState() == 0, Coupon::getStartTime, LocalDateTime.now());
            wrapper.and(request.getState() == 1, queryWrapper -> {
                LocalDateTime now = LocalDateTime.now();
                queryWrapper.ge(Coupon::getStartTime, now);
                queryWrapper.le(Coupon::getEndTime, now);
            });
            wrapper.lt(request.getState() == 2, Coupon::getEndTime, LocalDateTime.now());
        }
        wrapper.gt(Boolean.TRUE.equals(request.getInStock()), Coupon::getStock, 0);
        // mybatisPlus value值没有懒校验模式, 需要外层判断request.getMode是否为空, 否则CouponMode.valueOf会空指针
        if (request.getMode() != null) {
            wrapper.eq(Coupon::getMode, request.getMode());
        }
        wrapper.last(" order by state desc, id desc ");
        return couponMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(CouponAddRequest request) {
        Coupon config = DataUtil.copy(request, Coupon.class);
        couponMapper.insert(config);
        couponScopeService.insert(config.getId(), request.getItemIds());
    }

    @Override
    public void update(CouponEditRequest request) {
        Coupon config = DataUtil.copy(request, Coupon.class);
        couponMapper.updateById(config);
        couponScopeService.insertWithDelete(config.getId(), request.getItemIds());
    }

    @Override
    public void updateState(Long id, Integer state) {
        LambdaUpdateWrapper<Coupon> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Coupon::getId, id);
        wrapper.set(Coupon::getState, state);
        couponMapper.update(null, wrapper);
    }

    @Override
    public Coupon selectById(Long id) {
        return couponMapper.selectById(id);
    }

    @Override
    public Coupon selectByIdRequired(Long id) {
        Coupon config = this.selectById(id);
        if (config == null) {
            log.error("优惠券不存在 [{}]", id);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        return config;
    }

    @Override
    public List<CouponListVO> getByPage(CouponQueryDTO dto) {
        Page<CouponListVO> voPage = couponMapper.getByPage(dto.createPage(false), dto);
        List<CouponListVO> voList = voPage.getRecords();
        this.fillAttribute(voList);
        return voList;
    }

    @Override
    public List<CouponListVO> getItemCoupon(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            log.error("该零售商品已删除 [{}]", itemId);
            throw new BusinessException(ITEM_DOWN);
        }
        // 优惠券有店铺券或商品券之分
        List<CouponListVO> couponList = couponMapper.getItemCoupon(itemId, item.getStoreId());
        this.fillAttribute(couponList);
        return couponList;
    }

    /**
     * 填充优惠券是否已领取字段属性
     * @param couponList 优惠券信息
     */
    private void fillAttribute(List<CouponListVO> couponList) {
        Long memberId = ApiHolder.tryGetMemberId();
        // 用户未登陆, 默认全部可以领取
        if (memberId == null || CollUtil.isEmpty(couponList)) {
            return;
        }
        List<Long> couponIds = couponList.stream().map(CouponListVO::getId).collect(Collectors.toList());

        Map<Long, Integer> receivedMap = memberCouponService.countMemberReceived(memberId, couponIds);
        couponList.forEach(vo -> vo.setReceived(receivedMap.getOrDefault(vo.getId(), 0) >= vo.getMaxLimit()));
    }
}
