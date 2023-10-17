package com.eghm.vo.member;

import com.eghm.convertor.LongToIpEncoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class LoginDeviceVO {

	@ApiModelProperty("上次登陆时间 MM-dd HH:mm:ss")
	private String loginTime;

	@ApiModelProperty("登陆设备")
	private String deviceModel;

	@ApiModelProperty("设备唯一号")
	private String serialNumber;

	@ApiModelProperty("登陆ip")
	@JsonSerialize(using = LongToIpEncoder.class)
	private Long ip;
}
