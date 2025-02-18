package com.eghm.dto.business.scenic;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/14 22:24
 */
@Data
public class ScenicEditRequest {

    @Schema(description = "景区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "景区id不能为空")
    private Long id;

    @Schema(description = "景区名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "景区名称不能为空")
    @Size(min = 2, max = 20, message = "景区名称长度2~20位")
    @WordChecker(message = "景区名称存在敏感词")
    private String scenicName;

    @Schema(description = "景区等级 5:5A 4:4A 3:3A 2:2A 1:A 0:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {0, 1, 2, 3, 4, 5}, message = "景区等级格式错误")
    private Integer level;

    @Schema(description = "景区营业时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "营业时间长度最大20位")
    private String openTime;

    @Schema(description = "景区电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Phone(message = "景区电话格式错误")
    private String phone;

    @Schema(description = "景区标签")
    @WordChecker(message = "景区标签存在敏感词")
    private String tag;

    @Schema(description = "省份id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @Schema(description = "城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择城市")
    private Long cityId;

    @Schema(description = "县区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择县区")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址长度1~100位")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "维度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "景区描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 50, message = "景区描述信息最大50位")
    @WordChecker(message = "景区描述信息存在敏感词")
    private String depict;

    @Schema(description = "景区图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "景区图片不能为空")
    private List<String> coverList;

    @Schema(description = "详细介绍信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

}
