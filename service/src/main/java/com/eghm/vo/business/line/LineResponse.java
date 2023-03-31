package com.eghm.vo.business.line;

import com.eghm.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/12/26
 */
@Data
public class LineResponse {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "所属旅行社id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long travelAgencyId;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "出发省份id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long startCityId;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "虚拟销售量")
    private Integer virtualNum;

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

    @ApiModelProperty("线路每日行程")
    private List<LineDayConfigResponse> dayList;
}
