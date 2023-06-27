package com.eghm.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/3
 */
@Data
@ApiModel
public class SysAreaVO {

    @ApiModelProperty("区域id(唯一)")
    private Long id;

    @ApiModelProperty("区域名称")
    private String title;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("地区首字母")
    private String mark;

}
