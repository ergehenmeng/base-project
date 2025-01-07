package com.eghm.dto.business.homestay;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 14:31
 */
@Data
public class HomestayEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "民宿名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "民宿名称长度2~20位")
    @NotBlank(message = "民宿名称不能为空")
    @WordChecker(message = "民宿名称存在敏感词")
    private String title;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 2:二星级 0:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {0, 2, 3, 4, 5}, message = "民宿星级非法")
    private Integer level;

    @Schema(description = "省份", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "县区", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "详细地址长度2~50位")
    @NotBlank(message = "详细地址不能为空")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 50, message = "描述信息长度2~50位")
    @WordChecker(message = "描述信息存在敏感词")
    private String intro;

    @Schema(description = "封面图片,逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @Schema(description = "联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Phone(message = "联系电话格式错误")
    private String phone;

    @Schema(description = "入住须知")
    @NotBlank(message = "入住须知不能为空")
    @WordChecker
    private String notesIn;
    
    @Schema(description = "特色服务")
    private List<Integer> serviceList;

    @Schema(description = "标签,逗号分隔")
    @WordChecker
    private String tag;

}
