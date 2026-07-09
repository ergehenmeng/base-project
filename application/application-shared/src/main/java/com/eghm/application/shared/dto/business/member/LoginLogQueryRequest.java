package com.eghm.application.shared.dto.business.member;

import com.eghm.application.shared.annotation.DateFormatter;
import com.eghm.application.shared.configuration.gson.LocalDateAdapter;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogQueryRequest extends PagingQuery {

    @Schema(description = "会员id")
    @NotNull(message = "会员id不能为空")
    private Long memberId;

    @Schema(description = "登录渠道")
    private String channel;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
}
