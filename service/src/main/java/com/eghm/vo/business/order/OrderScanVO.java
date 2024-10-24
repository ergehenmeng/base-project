package com.eghm.vo.business.order;

import com.eghm.enums.ref.TicketType;
import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
public class OrderScanVO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品封面图")
    private String coverUrl;

    @ApiModelProperty("订单数量")
    private Integer num;

    @ApiModelProperty("总付款金额")
    private BigDecimal payAmount;

    @ApiModelProperty("游客列表")
    private List<VisitorVO> visitorList;

    @ApiModelProperty("组合门票(只有订单为门票且为组合票时才会有此项)")
    private List<CombineTicketVO> combineTicket;
}
