package com.eghm.dto.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Data
public class OrderVerifyDTO {

    @ApiModelProperty("核销码(加密订单号)")
    @NotBlank(message = "核销码不能为空")
    private String verifyNo;

    @ApiModelProperty("核销人员ID")
    private List<Long> visitorList;

    @ApiModelProperty("核销备注信息")
    private String remark;
}
