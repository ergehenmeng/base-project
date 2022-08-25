package com.eghm.service.business.handler.impl.restaurant;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.RestaurantOrder;
import com.eghm.dao.model.RestaurantVoucher;
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
        RestaurantOrder restaurantOrder = DataUtil.copy(product, RestaurantOrder.class);
        restaurantOrder.setOrderNo(order.getOrderNo());
        restaurantOrder.setVoucherId(product.getId());
        restaurantOrderService.insert(restaurantOrder);
        restaurantVoucherService.updateStock(product.getId(), -dto.getNum());
    }

    @Override
    protected RestaurantVoucher getProduct(OrderCreateDTO dto) {
        return restaurantVoucherService.selectByIdShelve(dto.getProductId());
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, RestaurantVoucher product) {
        BaseProduct baseProduct = new BaseProduct();
        baseProduct.setProductType(ProductType.VOUCHER);
        baseProduct.setTitle(product.getTitle());
        baseProduct.setHotSell(false);
        baseProduct.setSupportedCoupon(true);
        baseProduct.setRefundType(product.getRefundType());
        baseProduct.setSalePrice(product.getSalePrice());
        baseProduct.setRefundDescribe(product.getRefundDescribe());
        return baseProduct;
    }

    @Override
    protected void before(OrderCreateDTO dto, RestaurantVoucher product) {
        if (product.getStock() - dto.getNum() < 0) {
            log.error("餐饮券库存不足 [{}] [{}] [{}]", product.getId(), product.getStock(), dto.getNum());
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
        if (product.getQuota() < dto.getNum()) {
            log.error("超出餐椅券单次购买上限 [{}] [{}] [{}]", product.getId(), product.getQuota(), dto.getNum());
            throw new BusinessException(ErrorCode.VOUCHER_QUOTA.getCode(), String.format(ErrorCode.VOUCHER_QUOTA.getMsg(), product.getQuota()));
        }
    }
}
