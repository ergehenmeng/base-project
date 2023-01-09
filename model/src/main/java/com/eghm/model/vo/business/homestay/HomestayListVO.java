package com.eghm.model.vo.business.homestay;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
public class HomestayListVO {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    private String title;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @ApiModelProperty(value = "城市")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "县区")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

    @ApiModelProperty("距离 单位:m")
    private Integer distance;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty("最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty("标签")
    @JsonIgnore
    private String tag;

    @ApiModelProperty("标签列表")
    private List<String> tagList;

}
