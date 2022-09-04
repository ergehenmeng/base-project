package com.eghm.service.business.handler.impl.line;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.LineOrder;
import com.eghm.dao.model.Order;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineDaySnapshotService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.LineService;
import com.eghm.service.business.OrderMQService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.dto.LineOrderDTO;
import com.eghm.service.business.handler.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/2
 */
@Service("lineOrderCreateHandler")
@Slf4j
public class LineOrderCreateHandler extends AbstractOrderCreateHandler<LineOrderDTO> {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    private final LineDayConfigService lineDayConfigService;

    private final LineOrderService lineOrderService;

    private final LineDaySnapshotService lineDaySnapshotService;

    public LineOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineDaySnapshotService lineDaySnapshotService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.lineService = lineService;
        this.lineConfigService = lineConfigService;
        this.lineDayConfigService = lineDayConfigService;
        this.lineOrderService = lineOrderService;
        this.lineDaySnapshotService = lineDaySnapshotService;
    }

    @Override
    protected void next(OrderCreateDTO dto, LineOrderDTO product, Order order) {
        lineConfigService.updateStock(product.getConfig().getId(), -dto.getNum());
        LineOrder lineOrder = DataUtil.copy(product.getLine(), LineOrder.class);
        lineOrder.setOrderNo(order.getOrderNo());
        lineOrder.setLineConfigId(product.getConfig().getId());
        lineOrder.setLinePrice(product.getConfig().getLinePrice());
        lineOrder.setMobile(dto.getMobile());
        lineOrder.setNickName(dto.getNickName());
        lineOrderService.insert(lineOrder);
        lineDaySnapshotService.insert(dto.getProductId(), order.getOrderNo(), product.getDayList());
        
    }

    @Override
    protected LineOrderDTO getProduct(OrderCreateDTO dto) {
        LineOrderDTO orderDTO = new LineOrderDTO();
        orderDTO.setLine(lineService.selectByIdShelve(dto.getProductId()));
        orderDTO.setConfig(lineConfigService.getConfig(dto.getProductId(), dto.getConfigDate()));
        orderDTO.setDayList(lineDayConfigService.getByLineId(dto.getProductId()));
        return orderDTO;
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, LineOrderDTO product) {
        BaseProduct baseProduct = new BaseProduct();
        baseProduct.setTitle(product.getLine().getTitle());
        baseProduct.setProductType(ProductType.LINE);
        baseProduct.setHotSell(false);
        baseProduct.setRefundType(product.getLine().getRefundType());
        baseProduct.setRefundDescribe(product.getLine().getRefundDescribe());
        baseProduct.setSalePrice(product.getConfig().getSalePrice());
        baseProduct.setSupportedCoupon(true);
        baseProduct.setMultiple(false);
        baseProduct.setDeliveryType(DeliveryType.NO_SHIPMENT);
        return baseProduct;
    }

    @Override
    protected void before(OrderCreateDTO dto, LineOrderDTO product) {
        if (product.getConfig().getStock() - dto.getNum() < 0) {
            log.error("线路库存不足 [{}] [{}] [{}]", product.getConfig().getId(), product.getConfig().getStock(), dto.getNum());
            throw new BusinessException(ErrorCode.LINE_STOCK);
        }
    }
}
