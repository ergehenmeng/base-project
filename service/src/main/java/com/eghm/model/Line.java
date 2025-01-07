package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 线路商品信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
@TableName("line")
@EqualsAndHashCode(callSuper = true)
public class Line extends BaseEntity {

    @Schema(description = "所属旅行社id")
    private Long travelAgencyId;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @Schema(description = "出发省份id")
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    private Long startCityId;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "销售量")
    private Integer saleNum;

    @Schema(description = "总销量=实际销售+虚拟销量")
    private Integer totalNum;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 十日游 11:十一日游 12:十二日游")
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

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}
