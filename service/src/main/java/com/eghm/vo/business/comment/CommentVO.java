package com.eghm.vo.business.comment;

import com.eghm.convertor.DateParseEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentVO {

    @ApiModelProperty("留言id")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty(value = "评论信息")
    private String content;

    @ApiModelProperty("评论时间")
    @JsonSerialize(using = DateParseEncoder.class)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;

    @ApiModelProperty(value = "回复id")
    private Long replyId;

    @ApiModelProperty(value = "回复昵称")
    private String replyNickName;

    @ApiModelProperty(value = "回复头像")
    private String replyAvatar;

    @ApiModelProperty(value = "回复内容")
    private String replyContent;

    @ApiModelProperty("是否已点赞")
    private Boolean isLiked;
}
