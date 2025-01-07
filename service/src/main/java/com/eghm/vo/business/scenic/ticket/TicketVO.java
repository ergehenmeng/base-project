package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
public class TicketVO {

    @Schema(description = "门票id")
    private Long id;

    @Schema(description = "门票名称")
    private String title;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private TicketType category;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "提前多少天购票")
    private Integer advanceDay;

    @Schema(description = "单次最大购买数量")
    private Integer quota;

    @Schema(description = "开始预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "总销量=实际销量+虚拟销量")
    private Integer totalNum;

    @Schema(description = "门票介绍")
    private String introduce;

    @Schema(description = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @Schema(description = "分数")
    private BigDecimal score;

    @Schema(description = "组合票详细信息")
    private List<CombineTicketVO> combineList;
}
