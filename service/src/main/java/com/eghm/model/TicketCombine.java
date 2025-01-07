package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组合票关联表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ticket_combine")
@NoArgsConstructor
public class TicketCombine implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @Schema(description = "组合票ID")
    private Long ticketId;

    @Schema(description = "关联票ID")
    private Long relationId;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "删除状态 0:未删除 1:已删除")
    private String deleted;

    public TicketCombine(Long ticketId, Long relationId) {
        this.ticketId = ticketId;
        this.relationId = relationId;
    }
}
