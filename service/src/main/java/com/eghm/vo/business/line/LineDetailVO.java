package com.eghm.vo.business.line;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.RefundType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class LineDetailVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "旅行社logo")
    private String travelLogoUrl;

    @Schema(description = "旅行社名称")
    private String travelAgencyName;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "出发地")
    private String startPoint;

    @Schema(description = "起售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "销售量")
    private Integer totalNum;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:10日游 11:11日游 12:十二日游")
    private Integer duration;

    @Schema(description = "提前天数")
    private Integer advanceDay;

    @Schema(description = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "退款描述")
    private String refundDescribe;

    @Schema(description = "商品介绍")
    private String introduce;

    @Schema(description = "分数")
    private BigDecimal score;

    @Schema(description = "出发省份id")
    @JsonIgnore
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    @JsonIgnore
    private Long startCityId;

    @Schema(description = "线路每日行程")
    private List<LineDayConfigResponse> dayList;
}
