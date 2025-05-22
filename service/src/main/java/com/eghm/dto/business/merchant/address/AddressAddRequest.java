package com.eghm.dto.business.merchant.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2020/9/8
 */
@Data
public class AddressAddRequest {

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @ApiModelProperty(value = "收货人姓名", required = true)
    @Size(max = 10, message = "收货人姓名最大10字符")
    @NotBlank(message = "收货人姓名不能为空")
    private String nickName;

    @ApiModelProperty(value = "收货人手机号", required = true)
    @Mobile(message = "收货人手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "省份id", required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id", required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "地址类型 1: 收货地址 2: 自提地址")
    @NotNull(message = "请选择地址类型")
    private Integer addressType;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotEmpty(message = "详细地址不能为空")
    @Size(max = 50, message = "详细地址最大50字符")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty("备注信息")
    private String remark;
}
