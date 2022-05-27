package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区门票信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scenic_ticket")
@ApiModel(value="ScenicTicket对象", description="景区门票信息表")
public class ScenicTicket extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门票所属景区")
    private Long scenicId;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票")
    private Integer category;

    @ApiModelProperty(value = "门票封面图")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "成本价")
    private Integer costPrice;

    @ApiModelProperty(value = "提前多少天购票")
    private Integer advanceDay;

    @ApiModelProperty(value = "单次最大购买数量")
    private Integer quota;

    @ApiModelProperty(value = "开始预订时间")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止预订时间")
    private LocalDate endDate;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "真实销售数量")
    private Integer saleNum;

    @ApiModelProperty(value = "总销量=实际销量+虚拟销量")
    private Integer totalNum;

    @ApiModelProperty(value = "景区介绍")
    private String introduce;

    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效日期(包含)")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日")
    private Integer useScope;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    private Integer verificationType;

    @ApiModelProperty(value = "是否支持退款 1:支持 0:不支持")
    private Boolean supportRefund;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    private Boolean realBuy;

    @ApiModelProperty(value = "景区最低票价")
    private Integer minPrice;

    @ApiModelProperty(value = "景区状态 0:待上架 1:已上架")
    private Boolean state;


}
