package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 特产商品信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("specialty_product")
@ApiModel(value="SpecialtyProduct对象", description="特产商品信息")
public class SpecialtyProduct extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属特产店")
    private Long storeId;

    @ApiModelProperty(value = "商品状态 0:待下架 1:已上架")
    private Boolean state;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private Boolean deliveryMethod;

    @ApiModelProperty(value = "是否支持退款 true:支持 false:不支持")
    private Boolean supportRefund;

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
