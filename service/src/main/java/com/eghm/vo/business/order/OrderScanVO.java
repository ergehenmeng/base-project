package com.eghm.vo.business.order;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.SplitterArraySerializer;
import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @ApiModelProperty("订单数量")
    private Integer num;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @ApiModelProperty("游客列表")
    private List<VisitorVO> visitorList;

    @ApiModelProperty("套票门票(只有订单为门票且为套票票时才会有此项)")
    private List<CombineTicketVO> combineTicket;
}
