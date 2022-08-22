package com.eghm.service.business.handler.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.HomestayOrder;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.dao.model.Order;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.dto.HomestayOrderDTO;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/22
 */
@Service("homestayOrderCreateHandler")
@Slf4j
public class HomestayOrderCreateHandler extends AbstractOrderCreateHandler<HomestayOrderDTO> {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomService homestayRoomService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    public HomestayOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService, HomestayRoomConfigService homestayRoomConfigService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomService = homestayRoomService;
        this.homestayRoomConfigService = homestayRoomConfigService;
    }

    @Override
    protected void next(OrderCreateDTO dto, HomestayOrderDTO product, Order order) {
        homestayRoomConfigService.updateStock(dto.getProductId(), dto.getStartDate(), dto.getEndDate(), dto.getNum());
        HomestayOrder homestayOrder = DataUtil.copy(product.getHomestayRoom(), HomestayOrder.class);
        homestayOrder.setOrderNo(order.getOrderNo());
        homestayOrder.setMobile(dto.getMobile());
        homestayOrder.setStartDate(dto.getStartDate());
        homestayOrder.setEndDate(dto.getEndDate());
        homestayOrder.setRoomId(dto.getProductId());
        homestayOrderService.insert(homestayOrder);
    }

    @Override
    protected HomestayOrderDTO getProduct(OrderCreateDTO dto) {
        HomestayRoom homestayRoom = homestayRoomService.selectByIdShelve(dto.getProductId());
        List<HomestayRoomConfig> configList = homestayRoomConfigService.getList(dto.getProductId(), dto.getStartDate(), dto.getEndDate());
        HomestayOrderDTO product = new HomestayOrderDTO();
        product.setHomestayRoom(homestayRoom);
        product.setConfigList(configList);
        return product;
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, HomestayOrderDTO product) {
        // 民宿订单下单时数量默认为1
        dto.setNum(1);
        BaseProduct baseProduct = new BaseProduct();
        HomestayRoom room = product.getHomestayRoom();
        baseProduct.setProductType(ProductType.HOMESTAY);
        baseProduct.setRefundType(room.getRefundType());
        baseProduct.setHotSell(false);
        baseProduct.setSupportedCoupon(true);
        baseProduct.setTitle(room.getTitle());
        // 将每天的价格相加=总单价
        int salePrice = product.getConfigList().stream().mapToInt(HomestayRoomConfig::getSalePrice).sum();
        baseProduct.setSalePrice(salePrice);
        return baseProduct;
    }

    @Override
    protected void before(OrderCreateDTO dto, HomestayOrderDTO product) {
        List<HomestayRoomConfig> configList = product.getConfigList();
        long size = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        if (configList.size() < size) {
            log.error("该时间段房房态不满足下单数量 [{}] [{}] [{}] [{}]", dto.getProductId(), dto.getStartDate(), dto.getEndDate(), size);
            throw new BusinessException(ErrorCode.HOMESTAY_CONFIG_NULL);
        }
        boolean match = configList.stream().anyMatch(config -> Boolean.FALSE.equals(config.getState()) || config.getStock() <= 0);
        if (match) {
            log.error("房间库存不足 [{}] [{}] [{}]", dto.getProductId(), dto.getStartDate(), dto.getEndDate());
            throw new BusinessException(ErrorCode.HOMESTAY_STOCK);
        }
    }
}
