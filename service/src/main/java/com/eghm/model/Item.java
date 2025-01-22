package com.eghm.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "所属零售店id")
    private Long storeId;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "是否为推荐商品 true:是 false:否")
    private Boolean recommend;

    @Schema(description = "是否为多规格商品 true:是 false:不是")
    private Boolean multiSpec;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品描述信息")
    private String depict;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "购买须知")
    private String purchaseNotes;

    @Schema(description = "限购数量")
    private Integer quota;

    @Schema(description = "标签id")
    private String tagId;

    @Schema(description = "物流模板(为空表示包邮)")
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long expressId;

    @Schema(description = "交付方式 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @Schema(description = "最高价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer maxPrice;

    @Schema(description = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @Schema(description = "总销售量=实际销售+虚拟销量")
    private Integer totalNum;

    @Schema(description = "商品介绍信息")
    private String introduce;

    @Schema(description = "商品排序 越小越排在前面")
    private Integer sort;

    @Schema(description = "分数")
    private BigDecimal score;

    @Schema(description = "是否为热销商品")
    private Boolean hotSell;

    @Schema(description = "(拼团商品时,该字段不为空)拼团活动id")
    private Long bookingId;

    /**
     * 限时购与拼团互斥
     */
    @Schema(description = "(限时购晒商品时,该字段不能为空)限时购id")
    private Long limitId;

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}
