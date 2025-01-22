package com.eghm.dto.business.redeem;

import com.eghm.configuration.gson.LocalDateTimeAdapter;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.dto.ext.StoreScope;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/10
 */

@Data
public class RedeemCodeAddRequest {

    @Schema(description = "兑换码名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "兑换码名称不能为空")
    @Size(max = 20, message = "兑换码名称最大20字符")
    private String title;

    @Schema(description = "有效开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;

    @Schema(description = "有效截止时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer amount;

    @Schema(description = "发放数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "发放数量不能为空")
    @RangeInt(min = 1, max = 999, message = "发放数量应在1-999之间")
    private Integer num;

    @Schema(description = "使用范围")
    private List<StoreScope> storeList;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;
}
