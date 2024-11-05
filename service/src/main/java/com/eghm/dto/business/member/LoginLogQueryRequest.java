package com.eghm.dto.business.member;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogQueryRequest extends PagingQuery {

    @ApiModelProperty("会员id")
    @NotNull(message = "会员id不能为空")
    private Long memberId;

    @ApiModelProperty("登录渠道")
    private String channel;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;
}
