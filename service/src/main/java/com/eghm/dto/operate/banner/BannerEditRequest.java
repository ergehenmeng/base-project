package com.eghm.dto.operate.banner;

import com.eghm.configuration.gson.LocalDateTimeAdapter;
import com.eghm.enums.Channel;
import com.eghm.validation.annotation.OptionString;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/8/22 19:58
 */
@Data
public class BannerEditRequest {

    @Schema(description = "id不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "标题名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    @WordChecker(message = "标题存在敏感词")
    private String title;

    @Schema(description = "轮博图类型(数据字典)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "轮博图类型不能为空")
    private Integer bannerType;

    /**
     * 客户端类型 {@link Channel}
     */
    @Schema(description = "客户端类型 PC, ANDROID, IOS, H5, WECHAT", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionString({"IOS", "PC", "ANDROID", "H5", "WECHAT"})
    private String clientType;

    @Schema(description = "图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "图片地址不能为空")
    private String imgUrl;

    @Schema(description = "点击后跳转的地址")
    private String jumpUrl;

    @Schema(description = "开始展示时间(可在指定时间后开始展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "开始时间不能为空")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;

    @Schema(description = "取消展示的时间(只在某个时间段展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "结束时间不能为空")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    @Schema(description = "是否可点击 true:可以 false:不可以", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean click;

    @Schema(description = "备注信息")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
