package com.eghm.model.dto.business.product;

import com.eghm.common.enums.ref.RefundType;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;
import com.eghm.model.dto.business.product.sku.ItemSpecRequest;
import com.eghm.model.validation.annotation.OptionInt;
import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Data
public class ItemAddRequest {

    @ApiModelProperty(value = "商品名称")
    @Size(min = 2, max = 20, message = "商品名称长度2~20位")
    @NotBlank(message = "商品名称不能为空")
    private String title;

    @ApiModelProperty("店铺id")
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @ApiModelProperty(value = "商品描述信息")
    @Size(min = 5, max = 40, message = "商品描述信息长度5~40字符")
    @NotBlank(message = "商品描述信息不能为空")
    private String depict;
    
    @ApiModelProperty(value = "是否为多规格商品 true:是 false:不是")
    @NotNull(message = "规格类型不能为空")
    private Boolean multiSpec;

    @ApiModelProperty(value = "封面图")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "购买须知")
    @NotBlank(message = "购买须知不能为空")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    @RangeInt(min = 1, max = 99, message = "限购数量1~99之间")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    @OptionInt(value = {1, 2}, message = "交付方式不合法")
    private Integer deliveryMethod;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    @Size(max = 100, message = "退款描述信息最大100字符")
    private String refundDescribe;

    @ApiModelProperty(value = "商品介绍信息")
    @NotBlank(message = "商品介绍信息不能为空")
    private String introduce;
    
    @ApiModelProperty("sku列表")
    @NotEmpty(message = "sku不能为空")
    private List<ItemSkuRequest> skuList;
    
    @ApiModelProperty("规格列表")
    private List<ItemSpecRequest> specList;
}
