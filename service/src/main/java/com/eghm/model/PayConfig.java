package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.Channel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "客户端类型 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private Channel channel;

    @ApiModelProperty(value = "是否开启微信支付")
    private Boolean wechatPay;

    @ApiModelProperty(value = "是否开启支付宝支付")
    private Boolean aliPay;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
