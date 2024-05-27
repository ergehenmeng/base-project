package com.eghm.vo.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/9/7
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuBaseResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("按钮地址")
    private String subPath;

    @ApiModelProperty(value = "状态 true:启用 false:禁用")
    private Boolean state;

    @ApiModelProperty("菜单级别 1:导航 2:按钮")
    private Integer grade;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
