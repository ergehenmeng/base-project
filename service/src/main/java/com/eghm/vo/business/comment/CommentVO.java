package com.eghm.vo.business.comment;

import com.eghm.convertor.DateParseEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentVO {

    @Schema(description = "留言id")
    private Long id;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "评论信息")
    private String content;

    @Schema(description = "评论时间")
    @JsonSerialize(using = DateParseEncoder.class)
    private LocalDateTime createTime;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "被评论次数")
    private Integer replyNum;

    @Schema(description = "是否已点赞")
    private Boolean hasPraise;
}
