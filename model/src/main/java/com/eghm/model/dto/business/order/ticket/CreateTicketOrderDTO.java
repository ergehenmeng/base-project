package com.eghm.model.dto.business.order.ticket;

import com.eghm.model.annotation.Sign;
import com.eghm.model.dto.business.order.VisitorVO;
import com.eghm.model.validation.annotation.Mobile;
import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Data
public class CreateTicketOrderDTO {

    @ApiModelProperty("门票id")
    @NotNull(message = "门票不能为空")
    private Long ticketId;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @RangeInt(min = 1, max = 9, message = "门票购买数量1~9张")
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("游客信息列表")
    private List<VisitorVO> visitorList;

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;

}
