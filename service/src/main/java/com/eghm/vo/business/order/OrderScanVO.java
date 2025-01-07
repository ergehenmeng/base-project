package com.eghm.vo.business.order;

import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
public class OrderScanVO {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品封面图")
    private String coverUrl;

    @Schema(description = "订单数量")
    private Integer num;

    @Schema(description = "总付款金额")
    private BigDecimal payAmount;

    @Schema(description = "游客列表")
    private List<VisitorVO> visitorList;

    @Schema(description = "组合门票(只有订单为门票且为组合票时才会有此项)")
    private List<CombineTicketVO> combineTicket;
}
