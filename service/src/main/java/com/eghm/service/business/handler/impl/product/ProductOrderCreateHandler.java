package com.eghm.service.business.handler.impl.product;

import com.eghm.dao.model.Order;
import com.eghm.model.dto.business.order.BaseProductDTO;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.dto.ProductOrderDTO;
import com.eghm.service.business.handler.impl.AbstractOrderCreateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Service("productOrderCreateHandler")
@Slf4j
public class ProductOrderCreateHandler extends AbstractOrderCreateHandler<ProductOrderDTO> {

    private final ProductOrderService productOrderService;

    private final ProductService productService;

    private final ProductSkuService productSkuService;

    public ProductOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ProductOrderService productOrderService, ProductService productService, ProductSkuService productSkuService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.productOrderService = productOrderService;
        this.productService = productService;
        this.productSkuService = productSkuService;
    }

    @Override
    public void process(OrderCreateDTO dto) {
        super.process(dto);
    }

    @Override
    protected void next(OrderCreateDTO dto, ProductOrderDTO product, Order order) {

    }

    @Override
    protected ProductOrderDTO getProduct(OrderCreateDTO dto) {
        ProductOrderDTO product = new ProductOrderDTO();
        Set<Long> productIds = dto.getProductList().stream().map(BaseProductDTO::getProductId).collect(Collectors.toSet());
        product.setProductList(productService.getByIds(productIds));
        Set<Long> skuIds = dto.getProductList().stream().map(BaseProductDTO::getSkuId).collect(Collectors.toSet());
        product.setSkuList(productSkuService.getByIds(skuIds));
        return product;
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, ProductOrderDTO product) {
        return null;
    }

    @Override
    protected void before(OrderCreateDTO dto, ProductOrderDTO product) {

    }
}
