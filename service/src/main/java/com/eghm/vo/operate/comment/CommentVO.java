package com.eghm.vo.operate.comment;

import com.eghm.convertor.DateParseSerializer;
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
    @JsonSerialize(using = DateParseSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "被评论次数")
    private Integer replyNum;

    @ApiModelProperty("是否已点赞")
    private Boolean hasPraise;
}
