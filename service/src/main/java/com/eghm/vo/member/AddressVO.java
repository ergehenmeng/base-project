package com.eghm.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 殿小二
 * @since 2020/9/8
 */
@Data
public class AddressVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "城市名称")
    private String cityName;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "县区名称")
    private String countyName;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "状态 0:普通地址 1:默认地址")
    private Integer state;
}
