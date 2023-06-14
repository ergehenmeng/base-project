package com.eghm.dto.business.order.ticket;

import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class TicketOrderCreateDTO {

    @ApiModelProperty("门票id")
    @NotNull(message = "门票id不能为空")
    private Long ticketId;

    @ApiModelProperty("手机号码")
    @Mobile
    private String mobile;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    private LocalDate visitDate;

    @ApiModelProperty("游客信息")
    @Size(min = 1, max = 100, message = "游客数量最大100人")
    @NotEmpty(message = "游客信息不能为空")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
