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
public class NoticeTemplate extends BaseEntity {

    @ApiModelProperty("消息模板code")
    private String code;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String content;

}