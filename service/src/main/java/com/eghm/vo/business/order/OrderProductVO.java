package com.eghm.vo.business.order;

import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderProductVO {

    @ApiModelProperty(value = "商品订单id(零售使用)")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品封面图")
    private String coverUrl;

    @ApiModelProperty(value = "订单数量")
    private Integer num;

    @ApiModelProperty(value = "是否已核销 true:已核销 false:未核销(零售使用)")
    private Boolean verified;

    @ApiModelProperty(value = "游客列表(门票,民宿,线路)")
    private List<VisitorVO> visitorList;

    @ApiModelProperty(value = "套票门票(只有订单为门票且为套票票时才会有此项)")
    private List<CombineTicketVO> combineTicket;
}
