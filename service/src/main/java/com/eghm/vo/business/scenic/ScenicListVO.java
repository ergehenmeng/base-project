package com.eghm.vo.business.scenic;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @date 2022/7/11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScenicListVO {

    @ApiModelProperty("景区ID")
    private Long id;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("景区等级 5: 5A 4: 4A 3:3A 0:其他")
    private Integer level;

    @ApiModelProperty("景区信息描述")
    private String depict;

    @ApiModelProperty("最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty("距离 单位:m")
    private BigDecimal distance;

    @ApiModelProperty(value = "经度", hidden = true)
    @JsonIgnore
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", hidden = true)
    @JsonIgnore
    private BigDecimal latitude;

}
