package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单核销记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-06
 */
@Data
@TableName("verify_log")
public class VerifyLog {

    @Schema(description = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "核销人id")
    private Long userId;

    @Schema(description = "核销数量")
    private Integer num;

    @Schema(description = "核销备注")
    private String remark;

    @Schema(description = "核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
