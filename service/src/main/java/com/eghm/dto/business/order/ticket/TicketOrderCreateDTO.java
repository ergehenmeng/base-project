package com.eghm.dto.business.order.ticket;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.validation.annotation.AfterNow;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class TicketOrderCreateDTO {

    @Schema(description = "门票id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "门票id不能为空")
    private Long ticketId;

    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile
    private String mobile;

    @Schema(description = "游玩日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    @AfterNow(message = "请选择合适的游玩日期")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate visitDate;

    @Schema(description = "购票数量")
    @RangeInt(min = 1, max = 99, message = "购票数量最大99张")
    private Integer num;

    @Schema(description = "游客信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 99, message = "游客数量最大99人")
    private List<VisitorDTO> visitorList;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "备注信息")
    @Size(max = 50, message = "备注最大50字符")
    private String remark;
}
