package com.eghm.dto.business.order.homestay;

import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
public class HomestayOrderCreateDTO {

    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品不能为空")
    private Long roomId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "联系人电话", required = true)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "联系人姓名", required = true)
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @ApiModelProperty(value = "入住人员信息列表", required = true)
    @Size(min = 1, max = 99, message = "入住人数不能超过99人")
    @NotEmpty(message = "住房人信息不能为空")
    private List<VisitorDTO> visitorList;

    @ApiModelProperty(value = "入店日期(含晚上)", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "入店日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "离店日期(不含晚上)", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "离店日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty("备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
