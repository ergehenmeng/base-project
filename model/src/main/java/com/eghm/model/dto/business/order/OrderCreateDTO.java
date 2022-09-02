package com.eghm.model.dto.business.order;

import com.eghm.model.annotation.Sign;
import com.eghm.model.validation.annotation.Mobile;
import com.eghm.model.validation.group.HomestayOrderCreateGroup;
import com.eghm.model.validation.group.LineOrderCreateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Data
public class OrderCreateDTO {

    /**
     * 商品id,例如门票id,餐饮券id,房型id
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

    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空", groups = {HomestayOrderCreateGroup.class})
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空", groups = {HomestayOrderCreateGroup.class})
    private LocalDate endDate;

    @ApiModelProperty("联系人姓名")
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符", groups = LineOrderCreateGroup.class)
    private String nickName;

    @ApiModelProperty("出行日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出行日期不能为空", groups = {LineOrderCreateGroup.class})
    private LocalDate configDate;
}
