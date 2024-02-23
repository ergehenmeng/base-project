package com.eghm.dto.business.homestay.room;

import com.eghm.enums.ref.RefundType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2022/6/27
 */
@Data
public class HomestayRoomEditRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "房型id不能为空")
    private Long id;

    @ApiModelProperty(value = "房型名称", required = true)
    @Size(min = 2, max = 20, message = "房型名称应为2~20位")
    @NotBlank(message = "房型名称不能为空")
    @WordChecker(message = "房型名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "民宿id", required = true)
    @NotNull(message = "请选择所属民宿")
    private Long homestayId;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租", required = true)
    @NotNull(message = "房型类型不能为空")
    private Integer roomType;

    @ApiModelProperty(value = "封面图片", required = true)
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "订单确认方式: 1: 自动确认 2:手动确认", required = true)
    @OptionInt(value = {1, 2}, message = "订单确认方式不合法")
    private Integer confirmType;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款", required = true)
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    @WordChecker(message = "退款描述存在敏感词")
    private String refundDescribe;

    @ApiModelProperty(value = "几室", required = true)
    @NotNull(message = "户型参数不能为空")
    private Integer room;

    @ApiModelProperty(value = "几厅", required = true)
    @NotNull(message = "户型参数不能为空")
    private Integer hall;

    @ApiModelProperty(value = "几厨", required = true)
    @NotNull(message = "户型参数不能为空")
    private Integer kitchen;

    @ApiModelProperty(value = "卫生间数", required = true)
    @NotNull(message = "户型参数不能为空")
    private Integer washroom;

    @ApiModelProperty(value = "面积", required = true)
    @NotNull(message = "面积不能为空")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数", required = true)
    @NotNull(message = "户型不能为空")
    private Integer resident;

    @ApiModelProperty(value = "床数", required = true)
    @NotNull(message = "床数不能为空")
    private Integer bed;

    @ApiModelProperty(value = "屋内设施")
    @WordChecker(message = "屋内设置存在敏感词")
    private String infrastructure;

    @ApiModelProperty(value = "详细介绍", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    private String introduce;
}
