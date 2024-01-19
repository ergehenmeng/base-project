package com.eghm.service.business.handler.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Data
public class ItemOrderPayload {

    /**
     * 下单信息
     */
    private List<OrderPackage> packageList;

    /**
     * 优惠券id
     */
    @ApiModelProperty("优惠券id")
    private Long couponId;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String mobile;

    /**
     * 省份id
     */
    @ApiModelProperty("省份id")
    private Long provinceId;

    /**
     * 城市id
     */
    @ApiModelProperty("城市id")
    private Long cityId;

    /**
     * 县区id
     */
    @ApiModelProperty("县区id")
    private Long countyId;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String detailAddress;
}
