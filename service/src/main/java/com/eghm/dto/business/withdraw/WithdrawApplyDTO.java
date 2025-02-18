package com.eghm.dto.business.withdraw;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class WithdrawApplyDTO {

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;

    @Schema(description = "提现金额")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @Min(value = 10000, message = "最低提现金额100元")
    private Integer amount;

    @Schema(description = "银行卡所属用户姓名")
    private String realName;

    @Schema(description = "银行卡类型")
    private String bankType;

    @Schema(description = "银行卡号")
    private String bankNum;

    @Schema(description = "提现备注")
    private String remark;

}
