package com.eghm.dto.business.restaurant.voucher;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30 22:03
 */
@Data
public class VoucherAddRequest {

    @ApiModelProperty(value = "餐饮券名称", required = true)
    @Size(min = 2, max = 20, message = "餐饮券名称应为2~20字符")
    @NotBlank(message = "餐饮券名称不能为空")
    @WordChecker(message = "餐饮券名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "餐饮店铺", required = true)
    @NotNull(message = "店铺id不能为空")
    private Long restaurantId;

    @ApiModelProperty(value = "封面图片", required = true)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "划线价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价", required = true)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存", required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量", required = true)
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum = 0;

    @ApiModelProperty(value = "购买说明", required = true)
    @NotNull(message = "购买说明不能为空")
    @Size(max = 400, message = "购买说明最大400字符")
    @WordChecker(message = "购买说明存在敏感词")
    private String depict;

    @ApiModelProperty(value = "限购数量", required = true)
    @NotNull(message = "限购数量不能为空")
    @Max(value = 99, message = "限购数量不能大于99")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效时间(包含)")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间", required = true)
    @NotBlank(message = "开始使用时段不能为空")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间", required = true)
    @NotBlank(message = "结束使用时段不能为空")
    private String expireTime;

    @ApiModelProperty(value = "详细介绍", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
