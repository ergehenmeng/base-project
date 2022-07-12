package com.eghm.model.vo.scenic.ticket;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Data
public class TicketVO {

    @ApiModelProperty("门票id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票")
    private Integer category;

    @ApiModelProperty(value = "门票封面图")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
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

    @ApiModelProperty(value = "景区介绍")
    private String introduce;

    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日")
    private Integer useScope;

    @ApiModelProperty(value = "是否支持退款 1:支持 0:不支持")
    private Boolean supportRefund;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

}
