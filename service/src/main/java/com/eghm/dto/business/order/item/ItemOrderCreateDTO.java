package com.eghm.dto.business.order.item;

import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class ItemOrderCreateDTO {

    @ApiModelProperty("商品信息")
    @Size(min = 1, max = 99, message = "商品不能超过99种")
    @NotEmpty(message = "请选择商品")
    private List<ItemDTO> itemList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty("自提点Id")
    private Long pickUpId;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

}
