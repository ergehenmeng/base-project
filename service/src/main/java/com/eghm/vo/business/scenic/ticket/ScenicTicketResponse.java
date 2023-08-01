package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
public class ScenicTicketResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票所属景区")
    private Long scenicId;

    @ApiModelProperty(value = "门票上下架状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty("门票平台核销状态")
    private PlatformState platformState;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票")
    private Integer category;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "提前多少天购票")
    private Integer advanceDay;

    @ApiModelProperty(value = "单次最大购买数量")
    private Integer quota;

    @ApiModelProperty(value = "开始预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    private Integer verificationType;

    @ApiModelProperty(value = "退款方式 0:不支持退款 1:直接退款 2: 审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "是否实名购票 false:不实名 true:实名")
    private Boolean realBuy;

}
