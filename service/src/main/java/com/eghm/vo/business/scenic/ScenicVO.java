package com.eghm.vo.business.scenic;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2022/7/11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScenicVO {

    @Schema(description = "景区ID")
    private Long id;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "景区等级 5:5A 4:4A 3:3A 0:其他")
    private Integer level;

    @Schema(description = "景区信息描述")
    private String depict;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @Schema(description = "距离 单位:m")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal distance;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;

    @Schema(description = "经度", hidden = true)
    @JsonIgnore
    private BigDecimal longitude;

    @Schema(description = "纬度", hidden = true)
    @JsonIgnore
    private BigDecimal latitude;

}
