package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.RefundType;
import com.eghm.common.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product")
@ApiModel(value="Product对象", description="商品信息")
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属店铺")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品描述信息")
    private String describe;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private Integer deliveryMethod;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "最低价格")
    private Integer minPrice;

    @ApiModelProperty(value = "最高价格")
    private Integer maxPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @ApiModelProperty(value = "总销售量=实际销售+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;

}
