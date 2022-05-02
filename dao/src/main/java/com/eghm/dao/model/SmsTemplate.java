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
public class SmsTemplate extends BaseEntity {

    @ApiModelProperty("短信模板nid即短信类型")
    private String nid;

    @ApiModelProperty("短信内容")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;
}