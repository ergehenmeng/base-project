package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.group.*;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.GroupBookingMapper;
import com.eghm.model.GroupBooking;
import com.eghm.model.Item;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.GroupBookingService;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.common.JsonService;
import com.eghm.service.mq.service.MessageService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.group.GroupBookingDetailResponse;
import com.eghm.vo.business.group.GroupBookingResponse;
import com.eghm.vo.business.group.GroupItemVO;
import com.eghm.vo.business.group.GroupOrderCancelVO;
import com.eghm.vo.business.item.ItemSkuVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 拼团流程介绍:<br>
 * 1. 管理后台创建拼团活动并绑定单个零售商品, 设置活动时间, 拼团有效期, 拼团价格等信息. 进行中拼团无法编辑删除
 * 2. 如果用户使用拼团下单, 订单生成拼团单号, 并且可以分享拼团链接给其他用户, 其他用户可以加入拼团
 * 3. 其他用户创建拼团订单时需要携带拼团单号
 * 4. 所有拼团用户支付成功后, 拼团订单完成
 * 其他说明:<br>
 * 1. 拼团中, 拼团活动结束了, 拼团的订单全部取消 (失败)
 * 2. 拼团中, 团长退款, 当前的拼团订单全部退款 (失败)
 * 3. 拼团中, 团员退款, 只退当前团员的订单 (失败)
 * 4. 拼团成功, 团长或团员退单, 则只退款自己的拼团订单 (失败)
 * 5. 拼团中, 不管团员还是团长, 在未支付时取消订单则都会删除自己的拼团订单 (删除)
 * 6. 退款时拼团状态的更新均在退款审核通过更新
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

    private final MessageService messageService;

    private final ItemSkuService itemSkuService;

    @Override
    public Page<GroupBookingResponse> getByPage(GroupBookingQueryRequest request) {
        return groupBookingMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(GroupBookingAddRequest request) {
        this.checkTime(request.getStartTime(), request.getEndTime());
        this.redoTitle(request.getTitle(), null);
        itemService.checkBookingItem(request.getItemId());

        GroupBooking booking = DataUtil.copy(request, GroupBooking.class);
        booking.setSkuValue(jsonService.toJson(request.getSkuList()));
        booking.setMaxDiscountAmount(this.getMaxDiscountPrice(request.getSkuList()));
        groupBookingMapper.insert(booking);
        itemService.updateGroupBooking(request.getItemId(), booking.getItemId());
        this.sendExpireMessage(booking.getId(), booking.getEndTime(), null);
    }

    @Override
    public void update(GroupBookingEditRequest request) {
        this.checkTime(request.getStartTime(), request.getEndTime());
        this.redoTitle(request.getTitle(), request.getId());
        GroupBooking booking = groupBookingMapper.selectById(request.getId());
        // 防止非法操作
        commonService.checkIllegal(booking.getMerchantId());
        if (!booking.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.ACTIVITY_NOT_EDIT);
        }
        if (!booking.getItemId().equals(request.getItemId())) {
            // 校验新的商品是否是拼团商品
            itemService.checkBookingItem(request.getItemId());
            // 释放老的商品
            itemService.updateGroupBooking(booking.getItemId(), null);
            // 锁定新的商品
            itemService.updateGroupBooking(request.getItemId(), booking.getId());
        }

        GroupBooking groupBooking = DataUtil.copy(request, GroupBooking.class);
        groupBooking.setSkuValue(jsonService.toJson(request.getSkuList()));
        groupBooking.setMaxDiscountAmount(this.getMaxDiscountPrice(request.getSkuList()));
        groupBookingMapper.updateById(groupBooking);
        this.sendExpireMessage(booking.getId(), request.getEndTime(), booking.getEndTime());
    }

    @Override
    public void delete(Long id) {
        GroupBooking booking = groupBookingMapper.selectById(id);
        if (booking == null) {
            return;
        }
        if (LocalDateTime.now().isAfter(booking.getStartTime()) && LocalDateTime.now().isBefore(booking.getEndTime())) {
            log.warn("进行中的拼团不支持删除 [{}] [{}] [{}]", id, booking.getStartTime(), booking.getEndTime());
            throw new BusinessException(ErrorCode.BOOKING_DELETE);
        }
        LambdaUpdateWrapper<GroupBooking> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(GroupBooking::getId, id);
        wrapper.eq(GroupBooking::getMerchantId, SecurityHolder.getMerchantId());
        groupBookingMapper.delete(wrapper);
        itemService.updateGroupBooking(booking.getItemId(), null);
    }

    @Override
    public GroupBookingDetailResponse detail(Long id) {
        GroupBooking booking = this.getByIdRequired(id);
        Item item = itemService.selectByIdRequired(booking.getItemId());
        GroupBookingDetailResponse response = DataUtil.copy(booking, GroupBookingDetailResponse.class);
        response.setCoverUrl(item.getCoverUrl());
        response.setItemName(item.getTitle());
        List<ItemSkuVO> voList = itemSkuService.getByItemId(booking.getItemId());
        itemService.setGroupSkuPrice(voList, booking.getSkuValue());
        response.setSkuList(voList);
        return response;
    }

    @Override
    public List<GroupItemVO> listPage(GroupBookingQueryDTO dto) {
        Page<GroupItemVO> listPage = groupBookingMapper.listPage(dto.createPage(false), dto);
        return listPage.getRecords();
    }

    @Override
    public GroupBooking getValidById(Long bookingId) {
        GroupBooking booking = groupBookingMapper.getValidById(bookingId);
        if (booking == null) {
            log.warn("拼团未查询到价格信息 [{}]", bookingId);
            throw new BusinessException(ErrorCode.ITEM_GROUP_NULL);
        }
        return booking;
    }

    @Override
    public GroupBooking getById(Long bookingId) {
        return groupBookingMapper.getById(bookingId);
    }

    @Override
    public GroupBooking getByIdRequired(Long bookingId) {
        GroupBooking booking = groupBookingMapper.getById(bookingId);
        if (booking == null) {
            log.warn("拼团活动未查询到 [{}]", bookingId);
            throw new BusinessException(ErrorCode.BOOKING_NULL);
        }
        return booking;
    }

    @Override
    public Integer getFinalPrice(Long bookingId, Integer salePrice, Long skuId) {
        GroupBooking booking = groupBookingMapper.selectById(bookingId);
        if (booking == null) {
            log.info("拼团活动未查询到,以原价为准 [{}]", bookingId);
            return salePrice;
        }
        LocalDateTime now = LocalDateTime.now();
        if (booking.getEndTime().isBefore(now) || booking.getStartTime().isAfter(now)) {
            log.warn("拼团活动不在有效期 [{}] [{}] [{}]", bookingId, booking.getStartTime(), booking.getEndTime());
            return salePrice;
        }
        return this.getFinalPrice(booking.getSkuValue(), salePrice, skuId);
    }

    @Override
    public Integer getFinalPrice(String skuValue, Integer salePrice, Long skuId) {
        if (skuValue == null) {
            log.warn("拼团优惠金额为空");
            return salePrice;
        }
        List<GroupItemSkuRequest> skuList = jsonService.fromJsonList(skuValue, GroupItemSkuRequest.class);
        Map<Long, GroupItemSkuRequest> skuMap = skuList.stream().collect(Collectors.toMap(GroupItemSkuRequest::getSkuId, Function.identity()));
        GroupItemSkuRequest request = skuMap.get(skuId);
        if (request == null || !salePrice.equals(request.getSalePrice()) || request.getGroupPrice() == null) {
            log.warn("拼团sku价格信息未匹配 [{}]", skuId);
            return salePrice;
        }
        return request.getGroupPrice();
    }

    /**
     * 获取最大拼团优惠价格
     *
     * @param skuList sku列表
     * @return 最大优惠金额
     */
    private Integer getMaxDiscountPrice(List<GroupItemSkuRequest> skuList) {
        return skuList.stream().mapToInt(request -> request.getSalePrice() - request.getGroupPrice()).max().orElse(0);
    }

    /**
     * 校验时间
     *
     * @param startTime 开始日期
     */
    private void checkTime(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (startTime.isBefore(now)) {
            throw new BusinessException(ErrorCode.BOOKING_GT_TIME);
        }
        LocalDateTime dateTime = now.plusMonths(1);
        if (endTime.isAfter(dateTime)) {
            log.warn("拼团活动结束时间小于当前时间+1个月 [{}] [{}]", startTime, endTime);
            throw new BusinessException(ErrorCode.BOOKING_GT_MONTH);
        }
    }

    /**
     * 重复标题校验
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

    /**
     * 发送拼团过期消息
     *
     * @param bookingId 拼团活动id
     * @param newEndTime 新过期时间
     * @param oldEndTime 旧过期时间
     */
    private void sendExpireMessage(Long bookingId, LocalDateTime newEndTime, LocalDateTime oldEndTime) {
        // 为空表示新增的活动,需要发送, 否则为修改的活动, 新旧一样也无需发送
        if (oldEndTime == null || !oldEndTime.equals(newEndTime)) {
            int expireTime = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), newEndTime);
            messageService.sendDelay(ExchangeQueue.GROUP_ORDER_EXPIRE, new GroupOrderCancelVO(bookingId, newEndTime), expireTime);
        }
    }

}
