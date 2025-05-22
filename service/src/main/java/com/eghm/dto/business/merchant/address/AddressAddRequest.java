package com.eghm.dto.business.merchant.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2020/9/8
 */
@Data
public class AddressAddRequest {

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "收货人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 10, message = "收货人姓名最大10字符")
    @NotBlank(message = "收货人姓名不能为空")
    private String nickName;

    @Schema(description = "收货人手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile(message = "收货人手机号格式错误")
    private String mobile;

    @Schema(description = "省份id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "地址类型 1: 收货地址 2: 自提地址")
    @NotNull(message = "请选择地址类型")
    private Integer addressType;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "详细地址不能为空")
    @Size(max = 50, message = "详细地址最大50字符")
    private String detailAddress;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "备注信息")
    private String remark;
}
