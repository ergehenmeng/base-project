package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
public class TicketDetailResponse {

    @Schema(description = "门票id")
    private Long id;

    @Schema(description = "门票所属景区")
    private Long scenicId;

    @Schema(description = "门票状态 0:待上架 1:已上架 2: 强制下架")
    private State state;

    @Schema(description = "门票名称")
    private String title;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private Integer category;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "虚拟销量")
    private Integer virtualNum;

    @Schema(description = "单次最大购买数量")
    private Integer quota;

    @Schema(description = "提前多少天购票")
    private Integer advanceDay;

    @Schema(description = "开始预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "是否实名购票 false:不实名 true:实名")
    private Boolean realBuy;

    @Schema(description = "门票介绍")
    private String introduce;

}
