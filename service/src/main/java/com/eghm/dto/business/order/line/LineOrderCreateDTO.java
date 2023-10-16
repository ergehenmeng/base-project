package com.eghm.dto.business.order.line;

import com.eghm.annotation.Assign;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.validation.annotation.Mobile;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
public class LineOrderCreateDTO {

    @ApiModelProperty("用户ID")
    @Assign
    private Long memberId;

    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品不能为空")
    private Long lineId;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "联系人电话", required = true)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "联系人姓名", required = true)
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @ApiModelProperty(value = "线路人数列表", required = true)
    @Size(max = 99, message = "人数不能超过99人")
    private List<VisitorDTO> visitorList = new ArrayList<>();

    @ApiModelProperty(value = "游玩日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    private LocalDate configDate;

    @ApiModelProperty("备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
