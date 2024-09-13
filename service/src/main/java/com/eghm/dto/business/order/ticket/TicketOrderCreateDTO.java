package com.eghm.dto.business.order.ticket;

import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.validation.annotation.AfterNow;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class TicketOrderCreateDTO {

    @ApiModelProperty(value = "门票id", required = true)
    @NotNull(message = "门票id不能为空")
    private Long ticketId;

    @ApiModelProperty(value = "手机号码", required = true)
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "游玩日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    @AfterNow(message = "请选择合适的游玩日期")
    private LocalDate visitDate;

    @ApiModelProperty("购票数量")
    @RangeInt(min = 1, max = 99, message = "购票数量最大99张")
    private Integer num;

    @ApiModelProperty(value = "游客信息", required = true)
    @Size(max = 99, message = "游客数量最大99人")
    private List<VisitorDTO> visitorList = new ArrayList<>();

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("备注信息")
    @Size(max = 50, message = "备注最大50字符")
    private String remark;
}
