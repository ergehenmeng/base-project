package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 兑换码配置表 兑换码目前只针对于: 民宿, 线路, 场馆
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("redeem_code")
public class RedeemCode extends BaseEntity {

    @Schema(description = "cd名称")
    private String title;

    @Schema(description = "有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "有效截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @Schema(description = "发放数量")
    private Integer num;

    @Schema(description = "状态 0:待发放 1:已发放")
    private Integer state;

    @Schema(description = "备注信息")
    private String remark;
}
