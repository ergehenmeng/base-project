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

    /**
     * id
     */
    @ApiModelProperty("区域code(唯一)")
    private Long id;

    /**
     * 地区名称
     */
    @ApiModelProperty("区域名称")
    private String title;

    /**
     * 邮编
     */
    @ApiModelProperty("邮编")
    private String zipCode;

    /**
     * 首字母
     */
    @ApiModelProperty("地区首字母")
    private String mark;

}
