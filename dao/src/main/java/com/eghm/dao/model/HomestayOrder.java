package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 民宿订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay_order")
@ApiModel(value="HomestayOrder对象", description="民宿订单表")
public class HomestayOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "酒店id(冗余字段)")
    private Long homestayId;

    @ApiModelProperty(value = "房型id")
    private Long roomId;

    @ApiModelProperty(value = "房型名称")
    private String title;

    @ApiModelProperty(value = "单价")
    private Integer price;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "支付流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "开始时间(含)")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束时间(含)")
    private LocalDate endDate;

    @ApiModelProperty(value = "几室")
    private Boolean room;

    @ApiModelProperty(value = "几厅")
    private Boolean hall;

    @ApiModelProperty(value = "几厨")
    private Boolean kitchen;

    @ApiModelProperty(value = "卫生间数")
    private Boolean washroom;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "床数")
    private Boolean bed;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Boolean roomType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:支持")
    private Boolean supportRefund;

    @ApiModelProperty(value = "退款描述")
    private String refundDescribe;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付处理中 2:支付成功,待使用 3:已使用,待评价 4:已完成 5:已关闭")
    private Integer state;

    @ApiModelProperty(value = "退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功")
    private Boolean refundState;

    @ApiModelProperty(value = "关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private Boolean closeType;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

    @ApiModelProperty(value = "优惠金额")
    private Integer discountAmount;

    @ApiModelProperty(value = "付款金额=单价*数量-优惠金额")
    private Integer payAmount;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

}
