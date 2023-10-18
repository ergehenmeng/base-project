package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 餐饮代金券
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("restaurant_voucher")
public class RestaurantVoucher extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "餐饮商家id")
    private Long restaurantId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @ApiModelProperty(value = "商品名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "总销量=虚拟销量+真实销量")
    private Integer totalNum;

    @ApiModelProperty(value = "购买说明")
    private String depict;

    @ApiModelProperty(value = "单次限购数量")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起(与开始日期和截止日期互斥)")
    private Integer validDays;

    @ApiModelProperty(value = "使用开始日期(包含) yyyy-MM-dd")
    private LocalDate effectDate;

    @ApiModelProperty(value = "使用截止日期(包含) yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间 HH:mm")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间 HH:mm")
    private String expireTime;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

}
