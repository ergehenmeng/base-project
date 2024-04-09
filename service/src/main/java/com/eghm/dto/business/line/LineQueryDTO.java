package com.eghm.dto.business.line;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LineQueryDTO extends PagingQuery {

    @ApiModelProperty("出发省份")
    private Long startProvinceId;

    @ApiModelProperty("出发城市")
    private Long startCityId;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:10日游 11:11日游 12:十二日游")
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, required = false)
    private Integer duration;

    @ApiModelProperty("是否按销量排序")
    private Integer sortBySale;

    @ApiModelProperty("所属旅行社")
    private Long travelAgencyId;
}
