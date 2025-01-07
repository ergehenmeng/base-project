package com.eghm.vo.business.order.line;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class LineOrderSnapshotDetailVO {

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "所属旅行社名称")
    private String travelName;

    @Schema(description = "旅行社logo")
    private String travelLogoUrl;

    @Schema(description = "出发省份id")
    @JsonIgnore
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    @JsonIgnore
    private Long startCityId;

    @Schema(description = "出发地")
    private String startPoint;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:十日游 11:十一日游 12:十二日游")
    private Integer duration;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "商品介绍")
    private String introduce;

    @Schema(description = "线路配置列表")
    private List<LineOrderSnapshotVO> configList;
}
