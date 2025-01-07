package com.eghm.vo.member;

import com.eghm.convertor.LongToIpEncoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDeviceVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "上次登陆时间 MM-dd HH:mm:ss")
    private String loginTime;

    @Schema(description = "登陆设备")
    private String deviceModel;

    @Schema(description = "设备唯一号")
    private String serialNumber;

    @Schema(description = "登陆ip")
    @JsonSerialize(using = LongToIpEncoder.class)
    private Long ip;
}
