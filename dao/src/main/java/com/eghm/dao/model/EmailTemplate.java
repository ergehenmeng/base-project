package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EmailTemplate extends BaseEntity {

    @ApiModelProperty("模板唯一编码")
    private String nid;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;

}