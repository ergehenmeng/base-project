package com.eghm.service.business.handler.impl.restaurant;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.RestaurantOrder;
import com.eghm.dao.model.RestaurantVoucher;
import com.eghm.model.dto.business.order.BaseProductDTO;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/24
 */
@Service("restaurantOrderCreateHandler")
@Slf4j
public class RestaurantOrderCreateHandler extends AbstractOrderCreateHandler<RestaurantVoucher> {

    private final RestaurantVoucherService restaurantVoucherService;

    private final RestaurantOrderService restaurantOrderService;

    public RestaurantOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, RestaurantVoucherService restaurantVoucherService, RestaurantOrderService restaurantOrderService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.restaurantVoucherService = restaurantVoucherService;
        this.restaurantOrderService = restaurantOrderService;
    }

    @Override
    protected void next(OrderCreateDTO dto, RestaurantVoucher product, Order order) {
        BaseProductDTO base = dto.getFirstProduct();
        restaurantVoucherService.updateStock(product.getId(), -base.getNum());
        RestaurantOrder restaurantOrder = DataUtil.copy(product, RestaurantOrder.class);
        restaurantOrder.setOrderNo(order.getOrderNo());
        restaurantOrder.setVoucherId(product.getId());
        restaurantOrderService.insert(restaurantOrder);
    }

    @Override
    protected RestaurantVoucher getProduct(OrderCreateDTO dto) {
        return restaurantVoucherService.selectByIdShelve(dto.getFirstProduct().getProductId());
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, RestaurantVoucher product) {
        BaseProduct baseProduct = new BaseProduct();
        baseProduct.setProductType(ProductType.VOUCHER);
        baseProduct.setTitle(product.getTitle());
        baseProduct.setHotSell(false);
        baseProduct.setMultiple(false);
        baseProduct.setSupportedCoupon(true);
        baseProduct.setRefundType(product.getRefundType());
        baseProduct.setSalePrice(product.getSalePrice());
        baseProduct.setRefundDescribe(product.getRefundDescribe());
        baseProduct.setDeliveryType(DeliveryType.NO_SHIPMENT);
        return baseProduct;
    }

    @Override
    protected void before(OrderCreateDTO dto, RestaurantVoucher product) {
        Integer num = dto.getFirstProduct().getNum();
        if (product.getStock() - num < 0) {
            log.error("餐饮券库存不足 [{}] [{}] [{}]", product.getId(), product.getStock(), num);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
        if (product.getQuota() < num) {
            log.error("超出餐椅券单次购买上限 [{}] [{}] [{}]", product.getId(), product.getQuota(), num);
            throw new BusinessException(ErrorCode.VOUCHER_QUOTA.getCode(), String.format(ErrorCode.VOUCHER_QUOTA.getMsg(), product.getQuota()));
        }
    }
}
