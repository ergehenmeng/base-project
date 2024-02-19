package com.eghm.dto.business.account.score;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ChargeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScoreAccountQueryRequest extends PagingQuery {

    @ApiModelProperty("积分变动类型")
    private ChargeType chargeType;

    @ApiModelProperty(value = "1:收入 2:支出")
    private Integer direction;

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;
}
