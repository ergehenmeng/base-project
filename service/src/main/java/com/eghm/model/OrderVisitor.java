package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "游客使用状态")
    private VisitorState state;

    @Schema(description = "关联的核销记录id")
    private Long verifyId;

    @Schema(description = "游客姓名")
    private String memberName;

    @Schema(description = "身份证号码")
    private String idCard;

}
