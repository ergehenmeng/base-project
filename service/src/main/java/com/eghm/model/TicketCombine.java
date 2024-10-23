package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "组合票ID")
    private Long ticketId;

    @ApiModelProperty(value = "关联票ID")
    private Long relationId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除状态 0:未删除 1:已删除")
    private String deleted;

    public TicketCombine(Long ticketId, Long relationId) {
        this.ticketId = ticketId;
        this.relationId = relationId;
    }
}
