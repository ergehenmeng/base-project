package com.eghm.vo.business.scenic.ticket;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private TicketType category;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "提前多少天购票")
    private Integer advanceDay;

    @ApiModelProperty(value = "单次最大购买数量")
    private Integer quota;

    @ApiModelProperty(value = "开始预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "总销量=实际销量+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty("分数")
    private BigDecimal score;

    @ApiModelProperty("组合票详细信息")
    private List<CombineTicketVO> combineList;
}
