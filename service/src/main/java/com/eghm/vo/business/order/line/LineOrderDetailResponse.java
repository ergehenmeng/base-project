package com.eghm.vo.business.order.line;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.eghm.enums.ref.RefundType;
import com.eghm.vo.business.order.VisitorVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class LineOrderDetailResponse {

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "旅行社名称")
    private String travelName;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

    @Schema(description = "总优惠金额(优惠券)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountAmount;

    @Schema(description = "总付款金额=单价*数量-总优惠金额-兑换码优惠")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @Schema(description = "兑换码(只支持线路/民宿/场馆/餐饮)")
    private String cdKey;

    @Schema(description = "兑换码优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer cdKeyAmount;

    @Schema(description = "交易单号")
    private String tradeNo;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "联系人手机号")
    private String mobile;

    @Schema(description = "联系人姓名")
    private String nickName;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "预计游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10: 10日游 11:11日游 12:十二日游 13:十三日游 14:十四日游 15:十五日游")
    private Integer duration;

    @Schema(description = "出发省份")
    @JsonIgnore
    private Long startProvinceId;

    @Schema(description = "出发城市")
    @JsonIgnore
    private Long startCityId;

    @Schema(description = "出发省份城市")
    private String startProvinceCity;

    @Schema(description = "游客信息")
    private List<VisitorVO> visitorList;

    @Schema(description = "备注信息")
    private String remark;
}
