package com.eghm.dto.business.homestay.room;

import com.eghm.enums.RefundType;
import com.eghm.enums.RoomType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/27
 */
@Data
public class HomestayRoomAddRequest {

    @Schema(description = "民宿id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "民宿id不能为空")
    private Long homestayId;

    @Schema(description = "房型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "房型名称应为2~20位")
    @NotBlank(message = "房型名称不能为空")
    @WordChecker(message = "房型名称存在敏感词")
    private String title;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房型类型不能为空")
    private RoomType roomType;

    @Schema(description = "封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "订单确认方式: 1:自动确认 2:手动确认", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "订单确认方式不合法")
    private Integer confirmType;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @Schema(description = "退款描述")
    @WordChecker(message = "退款描述存在敏感词")
    private String refundDescribe;

    @Schema(description = "面积", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "面积不能为空")
    private Integer dimension;

    @Schema(description = "居住人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "户型不能为空")
    private Integer resident;

    @Schema(description = "屋内设施")
    private List<String> infrastructureList;

    @Schema(description = "详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

}
