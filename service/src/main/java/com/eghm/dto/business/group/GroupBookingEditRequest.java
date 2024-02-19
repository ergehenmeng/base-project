package com.eghm.dto.business.group;

import com.eghm.dto.ext.GroupItemSku;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupBookingEditRequest {

    @ApiModelProperty("活动id")
    @NotNull(message = "活动id不能为空")
    private Long id;

    @ApiModelProperty(value = "零售id")
    @NotNull(message = "请选择商品")
    private Long itemId;

    @ApiModelProperty(value = "活动名称")
    @NotBlank(message = "活动名称不能为空")
    @Size(max = 20, message = "活动名称最大20字符")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "拼团人数")
    @NotNull(message = "拼团人数不能为空")
    @RangeInt(min = 2, max = 99, message = "拼团人数必须在2-99之间")
    private Integer num;

    @ApiModelProperty(value = "拼团有效期,单位:分钟")
    @NotNull(message = "拼团有效期不能为空")
    private Integer expireTime;

    @ApiModelProperty("sku拼团优惠json")
    @NotEmpty(message = "拼团优惠不能为空")
    private List<GroupItemSku> skuList;
}
