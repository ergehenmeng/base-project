package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.NumberParseEncoder;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDetailVO {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty(value = "所属零售店id")
    private Long storeId;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("零售店铺名称")
    private String storeName;

    @ApiModelProperty(value = "商品状态 0:下架 1:上架")
    private Integer itemState;

    @ApiModelProperty("是否收藏")
    private Boolean collect;

    @ApiModelProperty("商品状态 0:待上架 1:已上架 2: 强制下架")
    @JsonIgnore
    private State state;

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

    @ApiModelProperty("物流模板(为空表示包邮)")
    private Long expressId;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty(value = "总销售量=实际销售+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty("是否为热销商品")
    private Boolean hotSell;

    @ApiModelProperty("评论总数量")
    @JsonSerialize(using = NumberParseEncoder.class)
    private Long commentNum;

    @ApiModelProperty("好评率百分比")
    private Integer goodRate;

    @ApiModelProperty("是否为拼团商品")
    private Boolean groupBooking = false;

    @ApiModelProperty("拼团活动id")
    @JsonIgnore
    private Long bookingId;

    @ApiModelProperty("是否为限时购商品")
    private Boolean limitPurchase = false;

    @ApiModelProperty("限时购活动id")
    @JsonIgnore
    private Long limitId;

    @ApiModelProperty("限时购开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("限时购结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("系统时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime systemTime;

    @ApiModelProperty("多规格信息")
    private List<ItemSpecVO> specList;

    @ApiModelProperty("规格sku信息(单规格时只有一条)")
    private List<ItemSkuVO> skuList;

}
