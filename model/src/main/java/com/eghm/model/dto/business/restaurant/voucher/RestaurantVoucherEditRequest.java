package com.eghm.model.dto.business.restaurant.voucher;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.eghm.common.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @date 2022/6/30 22:03
 */
@Data
public class RestaurantVoucherEditRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @Size(min = 2, max = 20, message = "餐饮券名称不能为空")
    private String title;

    @ApiModelProperty(value = "封面图片")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量")
    @Min(value = 0, message = "虚拟销量不能小于0")
    @NotNull(message = "虚拟销量不能为空")
    private Integer virtualNum;

    @ApiModelProperty(value = "购买说明")
    @NotNull(message = "购买说明不能为空")
    private String describe;

    @ApiModelProperty(value = "限购数量")
    @NotNull(message = "限购数量不能为空")
    private Integer quota;

    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效时间(包含)")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用开始时间")
    @NotBlank(message = "开始使用时间不能为空")
    private String effectTime;

    @ApiModelProperty(value = "使用截止时间")
    @NotBlank(message = "结束使用时间不能为空")
    private String expireTime;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "详细介绍")
    @NotBlank(message = "退订规则不能为空")
    private String introduce;
}
