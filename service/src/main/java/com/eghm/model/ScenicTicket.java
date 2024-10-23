package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.eghm.enums.ref.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 景区门票信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@TableName("scenic_ticket")
@EqualsAndHashCode(callSuper = true)
public class ScenicTicket extends BaseEntity {

    @ApiModelProperty(value = "门票所属景区")
    private Long scenicId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    private TicketType category;

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

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

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "真实销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "总销量=实际销量+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty("创建日期")
    private LocalDate createDate;

    @ApiModelProperty(value = "创建月份")
    private String createMonth;
}
