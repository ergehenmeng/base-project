package com.eghm.integration.messaging.dto;

import com.eghm.foundation.core.annotation.DateFormatter;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.enums.TemplateType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2019/8/21 16:21
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SmsLogQueryRequest extends PagingQuery {

    @Schema(description = "开始日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @Schema(description = "开始日期 yyyy-MM-dd")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    @Schema(description = "短信发送状态 0:发送中 1:发送成功 2:发送失败")
    private Integer state;

    @Schema(description = "短信类型")
    private TemplateType templateType;
}