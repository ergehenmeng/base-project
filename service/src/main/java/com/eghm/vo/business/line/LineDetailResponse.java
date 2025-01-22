package com.eghm.vo.business.line;

import com.eghm.enums.RefundType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/12/26
 */
@Data
public class LineDetailResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "出发省份id")
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    private Long startCityId;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "虚拟销售量")
    private Integer virtualNum;

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

    @Schema(description = "线路每日行程")
    private List<LineDayConfigResponse> configList;
}
