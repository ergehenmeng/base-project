package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 线路订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-01
 */
@Data
@TableName("line_order")
public class LineOrder {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "线路id")
    private Long lineId;

    @ApiModelProperty("线路配置id(冗余字段)")
    private Long lineConfigId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty("游玩日期")
    private LocalDate visitDate;

    @ApiModelProperty(value = "所属旅行社id")
    private Long travelAgencyId;

    @ApiModelProperty(value = "出发省份id")
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    private Long startCityId;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;

    @ApiModelProperty(value = "提前天数")
    private Integer advanceDay;

    @ApiModelProperty("划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "商品介绍")
    private String introduce;

}
