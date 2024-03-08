package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单游客信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-27
 */
@Data
@TableName("order_visitor")
@EqualsAndHashCode(callSuper = true)
public class OrderVisitor extends BaseEntity {

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty("游客使用状态")
    private VisitorState state;

    @ApiModelProperty("关联的核销记录id")
    private Long verifyId;

    @ApiModelProperty(value = "游客姓名")
    private String memberName;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

}
