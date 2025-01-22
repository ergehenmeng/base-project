package com.eghm.vo.business.comment;

import com.eghm.enums.ObjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty("置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @ApiModelProperty("评论对象id")
    private Long objectId;

    @ApiModelProperty("被举报次数")
    private Integer reportNum;

    @ApiModelProperty("评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @ApiModelProperty(value = "评论信息")
    private String content;

    @ApiModelProperty("评论时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

}
