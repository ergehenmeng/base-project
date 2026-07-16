package com.eghm.member.engagement.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Data
public class MemberScoreVO {

    @Schema(description = "积分值")
    private Integer score;

    @Schema(description = "变更后的积分")
    private Integer surplusScore;

    @Schema(description = "积分类型")
    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "积分发放或消费时间")
    private LocalDateTime createTime;

    @Schema(description = "积分发放或消费备注")
    private String remark;
}
