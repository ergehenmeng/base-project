package com.eghm.service.business.handler.impl.homestay;

import com.eghm.dao.model.HomestayOrder;
import com.eghm.dao.model.Order;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderExpireHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayOrderExpireHandler")
@Slf4j
public class HomestayOrderExpireHandler extends DefaultOrderExpireHandler {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    public HomestayOrderExpireHandler(OrderService orderService, UserCouponService userCouponService, HomestayOrderService homestayOrderService, HomestayRoomConfigService homestayRoomConfigService) {
        super(orderService, userCouponService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomConfigService = homestayRoomConfigService;
    }

    @Override
    protected void after(Order order) {
        HomestayOrder ticketOrder = homestayOrderService.selectByOrderNo(order.getOrderNo());
        homestayRoomConfigService.updateStock(ticketOrder.getRoomId(), ticketOrder.getStartDate(), ticketOrder.getEndDate(), -order.getNum());
    }
}
