package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 订单游客信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_visitor")
@ApiModel(value="OrderVisitor对象", description="订单游客信息表")
public class OrderVisitor extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty("状态 0: 初始化(待支付) 1: 待使用 2:已使用 3:已退款")
    private VisitorState state;

    @ApiModelProperty("关联id(退款记录id或核销记录id)")
    private Long collectId;

    @ApiModelProperty(value = "游客姓名")
    private String userName;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

}
