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
public class PushTemplate extends BaseEntity {

    @ApiModelProperty("消息名称")
    private String title;

    @ApiModelProperty("消息nid")
    private String nid;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Byte state;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("后置处理标示符(消息推送跳转页面)")
    private String tag;

    @ApiModelProperty("备注信息")
    private String remark;

}