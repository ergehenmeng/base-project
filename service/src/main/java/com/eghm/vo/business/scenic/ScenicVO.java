package com.eghm.vo.business.scenic;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2022/7/11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScenicVO {

    @ApiModelProperty("景区ID")
    private Long id;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("景区等级 5:5A 4:4A 3:3A 0:其他")
    private Integer level;

    @ApiModelProperty("景区信息描述")
    private String depict;

    @ApiModelProperty("最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @ApiModelProperty("距离 单位:m")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal distance;

    @ApiModelProperty("状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;

    @ApiModelProperty(value = "经度", hidden = true)
    @JsonIgnore
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", hidden = true)
    @JsonIgnore
    private BigDecimal latitude;

}
