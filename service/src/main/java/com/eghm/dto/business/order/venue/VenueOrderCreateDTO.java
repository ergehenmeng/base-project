package com.eghm.dto.business.order.venue;

import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Data
public class VenueOrderCreateDTO {

    @ApiModelProperty("场次id")
    @NotNull(message = "请选择场次")
    private Long venueSiteId;

    @ApiModelProperty("时间段id")
    @NotEmpty(message = "请选择时间段")
    private List<Long> phaseList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人姓名")
    @Size(max = 10, message = "联系人姓名最大10字符")
    private String nickName;

    @ApiModelProperty(value = "联系人电话", required = true)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty("备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
