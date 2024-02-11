package com.eghm.vo.business.redeem;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/11
 */
@Data
public class RedeemCodeGrantResponse {

    @ApiModelProperty("cdKey")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String cdKey;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("使用状态 0:未使用 1:已使用 2:已过期")
    private Integer state;

    @ApiModelProperty("使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

    @ApiModelProperty("发放时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
