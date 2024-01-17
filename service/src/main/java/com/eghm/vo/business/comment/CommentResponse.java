package com.eghm.vo.business.comment;

import com.eghm.convertor.DateParseEncoder;
import com.eghm.enums.ref.ObjectType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentResponse {

    @ApiModelProperty("留言id")
    private Long id;

    @ApiModelProperty("评论对象标题")
    private String objectName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("状态 0:已屏蔽 1:正常")
    private Boolean state;

    @ApiModelProperty("评论对象id")
    private Long objectId;

    @ApiModelProperty("评论对象id")
    private ObjectType objectType;

    @ApiModelProperty(value = "评论信息")
    private String content;

    @ApiModelProperty("评论时间")
    @JsonSerialize(using = DateParseEncoder.class)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;

}
