package com.eghm.vo.business.order.venue;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.eghm.enums.ref.VenueType;
import com.eghm.vo.business.venue.VenuePhaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/5
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VenueOrderDetailVO {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty(value = "场馆名称")
    private String title;

    @ApiModelProperty(value = "场地名称")
    private String siteTitle;

    @ApiModelProperty(value = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
    private VenueType venueType;

    @ApiModelProperty(value = "场馆封面图")
    private String coverUrl;

    @ApiModelProperty(value = "支付方式")
    private PayType payType;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功 5: 退款失败(该状态和退款中在C端用户看来都是退款中) 6: 线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @ApiModelProperty(value = "总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @ApiModelProperty(value = "总付款金额=单价*数量+总快递费-总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty("预约列表")
    private List<VenuePhaseVO> phaseList;

    @ApiModelProperty(value = "县区id")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty(value = "城市id")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "预约的时间段及价格(json)")
    @JsonIgnore
    private String timePhase;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @ApiModelProperty("核销码")
    private String verifyNo;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;
}
