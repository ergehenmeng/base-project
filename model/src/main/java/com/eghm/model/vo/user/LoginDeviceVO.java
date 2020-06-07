package com.eghm.model.vo.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@Builder
public class LoginDeviceVO {

	/**
	 * 登陆日期
	 */
	private String date;

	/**
	 * 设备型号
	 */
	private String deviceModel;
}
