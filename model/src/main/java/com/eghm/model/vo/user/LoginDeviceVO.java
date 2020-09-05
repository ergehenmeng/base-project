package com.eghm.model.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
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

	/**
	 * 上次登陆时间
	 */
	@ApiModelProperty("上次登陆时间 MM-dd HH:mm:ss")
	private String loginTime;

	/**
	 * 设备型号
	 */
	@ApiModelProperty("登陆设备")
	private String deviceModel;

	/**
	 * 设备唯一序列号
	 */
	@ApiModelProperty("设备唯一号")
	private String serialNumber;
}
