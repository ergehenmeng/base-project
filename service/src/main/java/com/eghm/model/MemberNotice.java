package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户站内信
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("member_notice")
public class MemberNotice extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("站内信内容")
    private String content;

    @ApiModelProperty("站内信分类")
    private String classify;

    @ApiModelProperty("状态 0:未读 1:已读")
    private Boolean isRead;

}