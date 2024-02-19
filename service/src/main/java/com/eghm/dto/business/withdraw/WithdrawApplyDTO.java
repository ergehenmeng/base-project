package com.eghm.dto.business.withdraw;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class WithdrawApplyDTO {

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("提现金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer amount;

    @ApiModelProperty(value = "银行卡所属用户姓名")
    private String realName;

    @ApiModelProperty(value = "银行卡类型")
    private String bankType;

    @ApiModelProperty(value = "银行卡号")
    private String bankNum;

    @ApiModelProperty("提现备注")
    private String remark;

}
