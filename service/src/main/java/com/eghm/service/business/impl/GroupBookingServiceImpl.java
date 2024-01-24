package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.group.GroupBookingAddRequest;
import com.eghm.dto.business.group.GroupBookingEditRequest;
import com.eghm.dto.business.group.GroupBookingQueryRequest;
import com.eghm.dto.business.group.SkuRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.GroupBookingMapper;
import com.eghm.model.GroupBooking;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.GroupBookingService;
import com.eghm.service.business.ItemService;
import com.eghm.service.common.JsonService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.group.GroupBookingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 拼团活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */

@Slf4j
@AllArgsConstructor
@Service("groupBookingService")
public class GroupBookingServiceImpl implements GroupBookingService {

    private final GroupBookingMapper groupBookingMapper;

    private final JsonService jsonService;

    private final ItemService itemService;

    private final CommonService commonService;

    @Override
    public Page<GroupBookingResponse> getByPage(GroupBookingQueryRequest request) {
        return groupBookingMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(GroupBookingAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        itemService.checkBookingItem(request.getItemId());

        GroupBooking booking = DataUtil.copy(request, GroupBooking.class);
        booking.setSkuValue(jsonService.toJson(request.getSkuList()));
        groupBookingMapper.insert(booking);
        itemService.updateGroupBooking(request.getItemId(), true, booking.getItemId());
    }

    @Override
    public void update(GroupBookingEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        GroupBooking booking = groupBookingMapper.selectById(request.getId());
        // 防止非法操作
        commonService.checkIllegal(booking.getMerchantId());

        if (!booking.getItemId().equals(request.getItemId())) {
            // 校验新的商品是否是拼团商品
            itemService.checkBookingItem(request.getItemId());
            // 释放老的商品
            itemService.updateGroupBooking(booking.getItemId(), false, null);
            // 锁定新的商品
            itemService.updateGroupBooking(request.getItemId(), true, booking.getId());
        }

        GroupBooking groupBooking = DataUtil.copy(request, GroupBooking.class);
        groupBooking.setSkuValue(jsonService.toJson(request.getSkuList()));
        groupBookingMapper.insert(groupBooking);

    }

    @Override
    public void delete(Long id) {
        GroupBooking booking = groupBookingMapper.selectById(id);
        if (booking == null) {
            return;
        }
        LambdaUpdateWrapper<GroupBooking> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(GroupBooking::getId, id);
        wrapper.eq(GroupBooking::getMerchantId, SecurityHolder.getMerchantId());
        groupBookingMapper.delete(wrapper);
        itemService.updateGroupBooking(booking.getItemId(), false, null);
    }

    @Override
    public Integer getGroupPrice(Long skuId, Long bookingId, Integer salePrice) {
        String skuValue = groupBookingMapper.getSkuValue(bookingId);
        if (skuValue == null) {
            log.warn("拼团未查询到价格信息 [{}]", bookingId);
            return salePrice;
        }
        List<SkuRequest> skuList = jsonService.fromJsonList(skuValue, SkuRequest.class);
        Map<Long, SkuRequest> skuMap = skuList.stream().collect(Collectors.toMap(SkuRequest::getSkuId, Function.identity()));
        SkuRequest request = skuMap.get(skuId);
        if (request == null || !salePrice.equals(request.getSalePrice()) || request.getGroupPrice() == null) {
            log.warn("拼团sku价格信息未匹配 [{}]", bookingId);
            return salePrice;
        }
        return request.getGroupPrice();
    }

    /**
     * 重置标题校验
     *
     * @param title 活动标题
     * @param id id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<GroupBooking> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GroupBooking::getTitle, title);
        wrapper.ne(id != null, GroupBooking::getId, id);
        Long count = groupBookingMapper.selectCount(wrapper);
        if (count > 0 ) {
            log.error("拼团活动名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.REDO_TITLE_BOOKING);
        }
    }

}
