package com.eghm.vo.business.line;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class LineDetailVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "所属旅行社id")
    private Long travelAgencyId;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty("出发地")
    private String startPoint;

    @ApiModelProperty("起售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "销售量")
    private Integer totalNum;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;

    @ApiModelProperty(value = "提前天数")
    private Integer advanceDay;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    private String refundDescribe;

    @ApiModelProperty(value = "商品介绍")
    private String introduce;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty("线路每日行程")
    private List<LineDayConfigResponse> dayList;
}
