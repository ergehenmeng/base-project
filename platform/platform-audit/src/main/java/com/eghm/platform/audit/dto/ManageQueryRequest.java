package com.eghm.platform.audit.dto;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/1/16 9:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ManageQueryRequest extends PagingQuery {

    @Schema(description = "开始时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @Schema(description = "结束时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;
}
