package com.eghm.model.vo.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/7
 */
@Data
public class MenuResponse {

    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private Long id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单标示符 唯一")
    private String code;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private Long pid;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("排序")
    @JsonIgnore
    private Integer sort;

    @ApiModelProperty("子菜单")
    private List<MenuResponse> children;
}
