package com.eghm.vo.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/3
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysAreaVO {

    @ApiModelProperty("区域id(唯一)")
    private Long id;

    @ApiModelProperty("区域名称")
    private String title;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("地区首字母")
    private String mark;

    @ApiModelProperty("父节点")
    private Long pid;

    @ApiModelProperty("子节点")
    private List<SysAreaVO> children;
}
