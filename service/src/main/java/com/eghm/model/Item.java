package com.eghm.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.DeliveryType;
import com.eghm.enums.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 零售商品信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
@Data
@TableName("item")
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    @ApiModelProperty(value = "所属零售店id")
    private Long storeId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "是否为推荐商品 true:是 false:否")
    private Boolean recommend;

    @ApiModelProperty(value = "是否为多规格商品 true:是 false:不是")
    private Boolean multiSpec;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品描述信息")
    private String depict;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty("物流模板(为空表示包邮)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long expressId;

    @ApiModelProperty(value = "交付方式 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer maxPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @ApiModelProperty(value = "总销售量=实际销售+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;

    @ApiModelProperty("商品排序 越小越排在前面")
    private Integer sort;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty("是否为热销商品")
    private Boolean hotSell;

    @ApiModelProperty("(拼团商品时,该字段不为空)拼团活动id")
    private Long bookingId;

    /**
     * 限时购与拼团互斥
     */
    @ApiModelProperty("(限时购晒商品时,该字段不能为空)限时购id")
    private Long limitId;

    @ApiModelProperty("创建日期")
    private LocalDate createDate;

    @ApiModelProperty(value = "创建月份")
    private String createMonth;
}
