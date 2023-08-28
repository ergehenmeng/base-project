package com.eghm.dto.business.item;

import com.eghm.enums.ref.RefundType;
import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
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
public class ItemEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("店铺id")
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @ApiModelProperty(value = "商品名称")
    @Size(min = 2, max = 20, message = "商品名称长度2~20位")
    @NotBlank(message = "商品名称不能为空")
    private String title;

    @ApiModelProperty("标签id")
    private String tagId;
    
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
    @OptionInt(value = {1, 2}, message = "交付方式不能为空")
    private Integer deliveryMethod;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    @Size(max = 100, message = "退款描述信息最大100字符")
    private String refundDescribe;

    @ApiModelProperty(value = "虚拟销量")
    @NotNull(message = "虚拟销量不能为空")
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum;

    @ApiModelProperty(value = "商品介绍信息")
    @NotBlank(message = "商品介绍信息不能为空")
    private String introduce;

    @ApiModelProperty("物流模板id(为空表示包邮)")
    private Long expressId;
    
    @ApiModelProperty("sku列表")
    @NotEmpty(message = "sku不能为空")
    private List<ItemSkuRequest> skuList;
    
    @ApiModelProperty("规格列表")
    private List<ItemSpecRequest> specList;
}
