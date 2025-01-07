package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class LineOrder extends BaseEntity {

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "创建人id")
    private Long memberId;

    @Schema(description = "线路id")
    private Long lineId;

    @Schema(description = "线路配置id(冗余字段)")
    private Long lineConfigId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "游玩日期")
    private LocalDate visitDate;

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "出发省份id")
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    private Long startCityId;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游")
    private Integer duration;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "商品介绍")
    private String introduce;

}
