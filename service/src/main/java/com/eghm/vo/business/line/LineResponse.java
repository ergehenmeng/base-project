package com.eghm.vo.business.line;

import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 线路商品信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
public class LineResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "所属旅行社名称")
    private Long travelAgencyTitle;

    @ApiModelProperty(value = "所属商户名称")
    private String merchantName;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "出发省份id")
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    private Long startCityId;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
