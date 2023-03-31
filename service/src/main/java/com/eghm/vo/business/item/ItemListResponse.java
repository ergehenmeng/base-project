package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
public class ItemListResponse {

    @ApiModelProperty(value = "商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "所属店铺")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 0:无须发货 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
