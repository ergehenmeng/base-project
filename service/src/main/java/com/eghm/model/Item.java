package com.eghm.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 零售商品信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item")
@ApiModel(value="Item对象", description="零售商品信息")
public class Item extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属特产店")
    private Long storeId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;
    
    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;
    
    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

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

    @ApiModelProperty(value = "交付方式 0:无须发货 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;
    
    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;
    
    @ApiModelProperty("退款描述信息")
    private String refundDescribe;
    
    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;
    
    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;
    
    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;
    
    @ApiModelProperty(value = "总销售量=实际销售+虚拟销量")
    private Integer totalNum;
    
    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;
    
    @ApiModelProperty("商品排序 越小越排在前面")
    private Integer sortBy;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty("是否为热销商品")
    private Boolean hotSell;
}
