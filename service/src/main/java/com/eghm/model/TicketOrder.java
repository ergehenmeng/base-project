package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 门票订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ticket_order")
@ApiModel(value="TicketOrder对象", description="门票订单表")
public class TicketOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("门票所属景区(冗余字段)")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long scenicId;
    
    @ApiModelProperty(value = "门票id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ticketId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "生效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime effectiveDate;

    @ApiModelProperty(value = "失效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expireDate;

    @ApiModelProperty(value = "使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日")
    private Integer useScope;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    private Integer verificationType;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    @ApiModelProperty("门票使用时间")
    private LocalDateTime useTime;
}
