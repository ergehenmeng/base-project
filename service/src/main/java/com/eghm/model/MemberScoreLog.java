package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("member_score_log")
public class MemberScoreLog {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "本次收入或支出的积分数")
    private Integer score;

    @Schema(description = "积分收入或支出分类")
    private Integer type;

    @Schema(description = "备注信息")
    private String remark;

}