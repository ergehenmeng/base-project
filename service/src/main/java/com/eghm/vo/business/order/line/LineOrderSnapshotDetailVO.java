package com.eghm.vo.business.order.line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class LineOrderSnapshotDetailVO {

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "所属旅行社id")
    private Long travelAgencyId;

    @ApiModelProperty(value = "所属旅行社名称")
    private String travelName;

    @ApiModelProperty(value = "旅行社logo")
    private String travelLogoUrl;

    @ApiModelProperty(value = "出发省份id")
    @JsonIgnore
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    @JsonIgnore
    private Long startCityId;

    @ApiModelProperty("出发地")
    private String startPoint;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;

    @ApiModelProperty(value = "提前天数")
    private Integer advanceDay;

    @ApiModelProperty("划线价")
    private Integer linePrice;

    @ApiModelProperty("销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "商品介绍")
    private String introduce;

    @ApiModelProperty(value = "线路配置列表")
    private List<LineOrderSnapshotVO> configList;
}
