package com.eghm.dto.business.line;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.State;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/8/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LineQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "所属旅行社id")
    private Long travelAgencyId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "出发省份id")
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    private Long startCityId;

    @ApiModelProperty(value = "1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 十日游 11:十一日游 12:十二日游 13:十三日游 14:十四日游 15:十五日游")
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, message = "旅游天数1~15天", required = false)
    private Integer duration;
}
