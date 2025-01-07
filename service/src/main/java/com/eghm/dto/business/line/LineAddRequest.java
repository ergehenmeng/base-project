package com.eghm.dto.business.line;

import com.eghm.enums.ref.RefundType;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
@Data
public class LineAddRequest {

    @Schema(description = "所属旅行社id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属旅行社不能为空")
    private Long travelAgencyId;

    @Schema(description = "线路名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "线路名称长度2~20字符")
    @NotBlank(message = "线路名称不能为空")
    @WordChecker(message = "线路名称包含敏感字")
    private String title;

    @Schema(description = "出发省份id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "出发省份不能为空")
    private Long startProvinceId;

    @Schema(description = "出发城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "出发城市不能为空")
    private Long startCityId;

    @Schema(description = "封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图片不能为空")
    private List<String> coverList;

    @Schema(description = "虚拟销售量", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum;

    @Schema(description = "几日游 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:十日游 11:十一日游 12:十二日游 13:十三日游 14:十四日游 15:十五日游", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, message = "旅游天数1~15天")
    private Integer duration;

    @Schema(description = "提前天数", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 30, message = "提前天数应为0~30天")
    private Integer advanceDay;

    @Schema(description = "是否支持退款 0:不支持 1:直接退款 2:审核后退款", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款方式不能为空")
    private RefundType refundType;

    @Schema(description = "退款描述")
    @Size(max = 100, message = "退款描述长度最大100字符")
    @WordChecker(message = "退款描述包含敏感字")
    private String refundDescribe;

    @Schema(description = "商品介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品介绍不能为空")
    @WordChecker(message = "商品介绍包含敏感字")
    @Expose(serialize = false)
    private String introduce;

    @Schema(description = "每日配置信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "每日线路配置不能为空")
    private List<LineDayConfigRequest> configList;
}
