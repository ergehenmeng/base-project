package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("email_template")
@EqualsAndHashCode(callSuper = true)
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