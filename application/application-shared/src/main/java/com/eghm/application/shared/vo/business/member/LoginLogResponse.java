package com.eghm.application.shared.vo.business.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Login log response for member management.
 */
@Data
public class LoginLogResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "登陆渠道")
    private String channel;

    @Schema(description = "登陆ip")
    private Long ip;

    @Schema(description = "设备厂商")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "软件版本")
    private String softwareVersion;

    @Schema(description = "设备唯一编号")
    private String serialNumber;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
