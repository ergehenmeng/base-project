package com.eghm.vo.business.homestay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/6/9
 */

@Data
public class HomestayDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    private String title;

    @ApiModelProperty(value = "民宿所属商家")
    private Long merchantId;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @ApiModelProperty(value = "省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市")
    private Long cityId;

    @ApiModelProperty(value = "县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息")
    private String intro;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "特色服务")
    private String keyService;

    @ApiModelProperty("入住须知")
    private String notesIn;

    @ApiModelProperty(value = "标签")
    private String tag;
}
