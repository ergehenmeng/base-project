package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登陆信息记录
 *
 * @author 二哥很猛
 * @since 2019/8/28 15:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecord {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("ip")
    private Long ip;

    @ApiModelProperty("登陆渠道")
    private String channel;

    @ApiModelProperty("设备厂商")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("软件版本")
    private String softwareVersion;

    @ApiModelProperty("设备唯一编号")
    private String serialNumber;
}
