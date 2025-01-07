package com.eghm.vo.business.line;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineVO {

    @Schema(description = "线路id")
    private Long id;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "旅行社logo")
    private String travelLogoUrl;

    @Schema(description = "旅行社名称")
    private String travelAgencyName;

    @Schema(description = "出发城市")
    private String startCity;

    @Schema(description = "出发城市(前端忽略该字段)")
    @JsonIgnore
    private Long startCityId;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "总销量")
    private Integer totalNum;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:10日游 11:11日游 12:十二日游")
    private Integer duration;

    @Schema(description = "状态 0:下架 1:上架 2:强制下架")
    private Integer state;
}
