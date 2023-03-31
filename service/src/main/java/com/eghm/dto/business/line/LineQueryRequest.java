package com.eghm.dto.business.line;

import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.dto.ext.PagingQuery;
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

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "出发省份id")
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    private Long startCityId;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;
}
