package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pay_config")
public class PayConfig {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "客户端类型 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private String channel;

    @Schema(description = "是否开启微信支付")
    private Boolean wechatPay;

    @Schema(description = "是否开启支付宝支付")
    private Boolean aliPay;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
