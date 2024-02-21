package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "cd名称")
    private String title;

    @ApiModelProperty(value = "有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "有效截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("金额")
    private Integer amount;

    @ApiModelProperty(value = "发放数量")
    private Integer num;

    @ApiModelProperty(value = "状态 0:待发放 1:已发放")
    private Integer state;
}
