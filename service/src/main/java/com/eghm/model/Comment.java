package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ObjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 评论记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@TableName("comment")
public class Comment {
    
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "评论对象ID")
    private Long objectId;

    @Schema(description = "状态 0:已屏蔽 1:正常")
    private Boolean state;

    @Schema(description = "置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @Schema(description = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "回复id")
    private Long replyId;

    @Schema(description = "评论信息")
    private String content;

    @Schema(description = "被举报次数")
    private Integer reportNum;

    @Schema(description = "父评论")
    private Long pid;

    @Schema(description = "评论级别 1:一级评论 2:二级评论")
    private Integer grade;

    @Schema(description = "被评论次数")
    private Integer replyNum;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;
    
    @TableLogic(delval = "1")
    @Schema(description = "是否已删除 0:未删除 1:已删除")
    @JsonIgnore
    private Boolean deleted;
}
