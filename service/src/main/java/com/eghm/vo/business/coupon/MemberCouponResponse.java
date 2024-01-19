package com.eghm.vo.business.coupon;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/7/20
 */

@Data
public class MemberCouponResponse {

    @ApiModelProperty("优惠券id")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty("使用状态 0:未使用 1:已使用 2:已过期")
    private Integer state;

    @ApiModelProperty("领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    @ApiModelProperty("使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

}
