package com.eghm.dto.business.item;

import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.enums.DeliveryType;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
public class ItemAddRequest {

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "商品名称长度2~20位")
    @NotBlank(message = "商品名称不能为空")
    @WordChecker(message = "商品名称存在敏感词")
    private String title;

    @Schema(description = "店铺id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @Schema(description = "标签id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择品类标签")
    private String tagId;

    @Schema(description = "商品描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 5, max = 40, message = "商品描述信息长度5~40字符")
    @NotBlank(message = "商品描述信息不能为空")
    @WordChecker(message = "商品描述信息存在敏感词")
    private String depict;

    @Schema(description = "是否为多规格商品 true:是 false:不是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规格类型不能为空")
    private Boolean multiSpec;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "购买须知", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "购买须知不能为空")
    @WordChecker(message = "购买须知存在敏感词")
    @Expose(serialize = false)
    private String purchaseNotes;

    @Schema(description = "限购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 999, message = "限购数量1~999之间")
    private Integer quota;

    @Schema(description = "交付方式 1:快递包邮 2:门店自提", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "交付方式不能为空")
    private DeliveryType deliveryType;

    @Schema(description = "商品介绍信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品介绍信息不能为空")
    @WordChecker(message = "商品介绍信息存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @Schema(description = "物流模板id(为空表示包邮)")
    private Long expressId;

    @Schema(description = "sku列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "sku不能为空")
    private List<ItemSkuRequest> skuList;

    @Schema(description = "规格列表")
    private List<ItemSpecRequest> specList;
}
