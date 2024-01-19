package com.eghm.dto.business.homestay.room;

import com.eghm.enums.ref.RefundType;
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
public class HomestayRoomAddRequest {

    @ApiModelProperty(value = "民宿id")
    @NotNull(message = "民宿id不能为空")
    private Long homestayId;

    @ApiModelProperty("房型名称")
    @Size(min = 2, max = 20, message = "房型名称应为2~20位")
    @NotBlank(message = "房型名称不能为空")
    @WordChecker(message = "房型名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "几室")
    @NotNull(message = "户型参数不能为空")
    private Integer room;

    @ApiModelProperty(value = "几厅")
    @NotNull(message = "户型参数不能为空")
    private Integer hall;

    @ApiModelProperty(value = "几厨")
    @NotNull(message = "户型参数不能为空")
    private Integer kitchen;

    @ApiModelProperty(value = "卫生间数")
    @NotNull(message = "户型参数不能为空")
    private Integer washroom;

    @ApiModelProperty(value = "面积")
    @NotNull(message = "面积不能为空")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    @NotNull(message = "户型不能为空")
    private Integer resident;

    @ApiModelProperty(value = "床数")
    @NotNull(message = "床数不能为空")
    private Integer bed;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    @NotNull(message = "房型类型不能为空")
    private Integer roomType;

    @ApiModelProperty(value = "封面图片")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "屋内设施")
    @WordChecker(message = "屋内设置存在敏感词")
    private String infrastructure;

    @ApiModelProperty(value = "详细介绍")
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    private String introduce;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    @WordChecker(message = "退款描述存在敏感词")
    private String refundDescribe;
}
