package com.eghm.dto.business.merchant.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/9/10
 */
@Data
public class AddressEditRequest {

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "地址id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

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

    @Schema(description = "县区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "详细地址不能为空")
    @Size(max = 50, message = "详细地址最大50字符")
    private String detailAddress;

    @Schema(description = "备注信息")
    private String remark;
}
