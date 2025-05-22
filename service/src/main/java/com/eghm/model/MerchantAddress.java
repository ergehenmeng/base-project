package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 商户收货地址表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("merchant_address")
public class MerchantAddress extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty("地址类型 1: 收货地址 2: 自提地址")
    private Integer addressType;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty("备注信息")
    private String remark;
}
