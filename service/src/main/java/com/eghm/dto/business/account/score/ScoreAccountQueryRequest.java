package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.enums.ref.ChargeType;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ScoreAccountQueryRequest extends DatePagingComparator {

    @Schema(description = "变动类型(1:充值 2:支付收入 3:支付退款 4:抽奖支出 5:提现支出 6:关注赠送 7:提现失败)")
    private ChargeType chargeType;

    @Schema(description = "1:收入 2:支出")
    private Integer direction;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;
}
