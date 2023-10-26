package com.eghm.vo.business.item;

import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.RefundType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 殿小二
 * @date 2023/3/7
 */

@Data
public class ItemResponse {
    
    @ApiModelProperty("id(编辑时不能为空)")
    private Long id;
    
    @ApiModelProperty(value = "商品名称")
    private String title;
    
    @ApiModelProperty(value = "商品描述信息")
    private String depict;
    
    @ApiModelProperty(value = "是否为多规格商品 true:是 false:不是")
    private Boolean multiSpec;
    
    @ApiModelProperty(value = "封面图")
    private String coverUrl;
    
    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;
    
    @ApiModelProperty(value = "限购数量")
    private Integer quota;
    
    @ApiModelProperty(value = "交付方式 1:快递包邮 2:门店自提 ")
    private DeliveryType deliveryType;
    
    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;
    
    @ApiModelProperty("退款描述信息")
    private String refundDescribe;
    
    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;
    
    @ApiModelProperty("规格信息(单规格为空)")
    private Map<String, List<ItemSpecResponse>> specMap;

    @ApiModelProperty("sku信息")
    private List<ItemSkuResponse> skuList;
}
