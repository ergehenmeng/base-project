package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 */
@Data
@TableName("tag_view")
public class TagView implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("页面名称")
    private String title;

    @ApiModelProperty("view唯一标示符")
    private String tag;

    @ApiModelProperty("view页面涉及到的接口,分号分割")
    private String url;

    @ApiModelProperty("备注信息")
    private String remark;

}