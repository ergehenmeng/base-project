package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "ip")
    private Long ip;

    @Schema(description = "登陆渠道")
    private String channel;

    @Schema(description = "设备厂商")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "软件版本")
    private String softwareVersion;

    @Schema(description = "设备唯一编号")
    private String serialNumber;
}
