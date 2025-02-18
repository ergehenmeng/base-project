package com.eghm.vo.business.scenic;

import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/3
 */

@Data
public class ScenicDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "景区名称")
    private String scenicName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3: 3A 0:其他")
    private Integer level;

    @ApiModelProperty("景区标签")
    private String tag;

    @ApiModelProperty(value = "景区营业时间")
    private String openTime;

    @ApiModelProperty("景区电话")
    private String phone;

    @ApiModelProperty(value = "景区图片")
    private String coverUrl;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区描述信息")
    private String depict;

    @ApiModelProperty(value = "景区详细介绍信息")
    private String introduce;

}
