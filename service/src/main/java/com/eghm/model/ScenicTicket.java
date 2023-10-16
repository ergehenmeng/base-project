package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 景区门票信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scenic_ticket")
@ApiModel(value="ScenicTicket对象", description="景区门票信息表")
public class ScenicTicket extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票所属景区")
    private Long scenicId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票")
    private Integer category;

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @ApiModelProperty(value = "门票封面图")
    private String coverUrl;

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

    @ApiModelProperty(value = "真实销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "总销量=实际销量+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    private Integer verificationType;

    @ApiModelProperty(value = "退款方式 0:不支持退款 1:直接退款 2: 审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;
}
