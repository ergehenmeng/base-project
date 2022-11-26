package com.eghm.model.dto.business.product;

import com.eghm.common.enums.ref.RefundType;
import com.eghm.model.dto.business.product.sku.ProductSkuRequest;
import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Data
public class ProductAddRequest {

    @ApiModelProperty(value = "商品名称")
    @Size(min = 2, max = 20, message = "商品名称长度2~20位")
    private String title;

    @ApiModelProperty(value = "商品描述信息")
    @Size(min = 5, max = 40, message = "商品描述信息长度5~40字符")
    private String depict;

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
    @NotNull(message = "交付方式不能为空")
    private Integer deliveryMethod;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "虚拟销量")
    @NotNull(message = "虚拟销量不能为空")
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum;

    @ApiModelProperty(value = "商品介绍信息")
    @NotBlank(message = "商品介绍信息不能为空")
    private String introduce;

    @Size(message = "规格不能为空")
    private List<ProductSkuRequest> skuList;
}
