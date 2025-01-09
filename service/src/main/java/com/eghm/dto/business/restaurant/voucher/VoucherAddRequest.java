package com.eghm.dto.business.restaurant.voucher;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30 22:03
 */
@Data
public class VoucherAddRequest {

    @Schema(description = "餐饮券名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "餐饮券名称应为2~20字符")
    @NotBlank(message = "餐饮券名称不能为空")
    @WordChecker(message = "餐饮券名称存在敏感词")
    private String title;

    @Schema(description = "餐饮店铺", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "店铺id不能为空")
    private Long restaurantId;

    @Schema(description = "封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "标签id")
    private Long tagId;

    @Schema(description = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @Schema(description = "剩余库存", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @Schema(description = "虚拟销量", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum = 0;

    @Schema(description = "购买说明", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买说明不能为空")
    @Size(max = 400, message = "购买说明最大400字符")
    @WordChecker(message = "购买说明存在敏感词")
    private String depict;

    @Schema(description = "限购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "限购数量不能为空")
    @Max(value = 99, message = "限购数量不能大于99")
    private Integer quota;

    @Schema(description = "有效期购买之日起")
    private Integer validDays;

    @Schema(description = "生效时间(包含)")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate effectDate;

    @Schema(description = "失效日期(包含)")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate expireDate;

    @Schema(description = "使用开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "开始使用时段不能为空")
    private String effectTime;

    @Schema(description = "使用截止时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "结束使用时段不能为空")
    private String expireTime;

    @Schema(description = "详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
