package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.CouponConfigMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.CouponConfig;
import com.eghm.model.Item;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.CouponScopeService;
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
@Service("couponConfigService")
@AllArgsConstructor
@Slf4j
public class CouponConfigServiceImpl implements CouponConfigService {

    private final CouponConfigMapper couponConfigMapper;

    private final CouponScopeService couponScopeService;

    private final MemberCouponService memberCouponService;

    private final ItemMapper itemMapper;

    @Override
    public Page<CouponConfig> getByPage(CouponConfigQueryRequest request) {
        LambdaQueryWrapper<CouponConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), CouponConfig::getTitle, request.getQueryName());
        if (request.getState() != null) {
            wrapper.gt( request.getState() == 0, CouponConfig::getStartTime, LocalDateTime.now());
            wrapper.and(request.getState() == 1, queryWrapper -> {
                LocalDateTime now = LocalDateTime.now();
                queryWrapper.ge(CouponConfig::getStartTime, now);
                queryWrapper.le(CouponConfig::getEndTime, now);
            });
            wrapper.lt(request.getState() == 2, CouponConfig::getEndTime, LocalDateTime.now());
        }
        wrapper.gt(Boolean.TRUE.equals(request.getInStock()), CouponConfig::getStock, 0);
        // mybatisPlus value值没有懒校验模式, 需要外层判断request.getMode是否为空, 否则CouponMode.valueOf会空指针
        if (request.getMode() != null) {
            wrapper.eq(CouponConfig::getMode, request.getMode());
        }
        wrapper.last(" order by state desc, id desc ");
        return couponConfigMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(CouponConfigAddRequest request) {
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.insert(config);
        couponScopeService.insert(config.getId(), request.getItemList());
    }

    @Override
    public void update(CouponConfigEditRequest request) {
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.updateById(config);
        couponScopeService.insertWithDelete(config.getId(), request.getItemList());
    }

    @Override
    public void updateState(Long id, Integer state) {
        LambdaUpdateWrapper<CouponConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponConfig::getId, id);
        wrapper.set(CouponConfig::getState, state);
        couponConfigMapper.update(null, wrapper);
    }

    @Override
    public CouponConfig selectById(Long id) {
        return couponConfigMapper.selectById(id);
    }

    @Override
    public CouponConfig selectByIdRequired(Long id) {
        CouponConfig config = this.selectById(id);
        if (config == null) {
            log.error("优惠券不存在 [{}]", id);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        return config;
    }

    @Override
    public List<CouponListVO> getByPage(CouponQueryDTO dto) {
        Page<CouponListVO> voPage = couponConfigMapper.getByPage(dto.createPage(false), dto);
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
        List<CouponListVO> couponList = couponConfigMapper.getItemCoupon(itemId, item.getStoreId());
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
