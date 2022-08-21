package com.eghm.model.dto.business.order;

import com.eghm.model.annotation.Sign;
import com.eghm.model.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Data
public class OrderCreateDTO {

    /**
     * 商品id,例如门票id,餐饮券id等
     */
    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long productId;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @Min(value = 1, message = "购买数量不能小于1张")
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("游客信息列表")
    private List<VisitorVO> visitorList;

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;

}
