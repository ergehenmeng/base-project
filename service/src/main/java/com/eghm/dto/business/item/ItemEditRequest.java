package com.eghm.dto.business.item;

import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
public class ItemEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "店铺id", required = true)
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @ApiModelProperty(value = "商品名称", required = true)
    @Size(min = 2, max = 20, message = "商品名称长度2~20位")
    @NotBlank(message = "商品名称不能为空")
    @WordChecker(message = "商品名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "标签id", required = true)
    @NotBlank(message = "请选择品类标签")
    private String tagId;

    @ApiModelProperty(value = "是否为多规格商品 true:是 false:不是", required = true)
    @NotNull(message = "规格类型不能为空")
    private Boolean multiSpec;

    @ApiModelProperty(value = "封面图", required = true)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "购买须知", required = true)
    @NotBlank(message = "购买须知不能为空")
    @WordChecker(message = "购买须知存在敏感词")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量", required = true)
    @RangeInt(min = 1, max = 99, message = "限购数量1~99之间")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 1:快递包邮 2:门店自提", required = true)
    @OptionInt(value = {1, 2}, message = "交付方式不合法")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "商品介绍信息", required = true)
    @NotBlank(message = "商品介绍信息不能为空")
    @WordChecker(message = "商品介绍信息存在敏感词")
    private String introduce;

    @ApiModelProperty("物流模板id(为空表示包邮)")
    private Long expressId;

    @ApiModelProperty(value = "sku列表", required = true)
    @NotEmpty(message = "sku不能为空")
    private List<ItemSkuRequest> skuList;

    @ApiModelProperty("规格列表")
    private List<ItemSpecRequest> specList;
}
