package com.eghm.dto.member.tag;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.enums.Channel;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class MemberTagEditRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标签名称不能为空")
    @Size(min = 2, max = 20, message = "标签名称长度2~20位")
    @WordChecker(message = "标签名称存在敏感词")
    private String title;

    @Schema(description = "注册开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "注册开始日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate registerStartDate;

    @Schema(description = "注册截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate registerEndDate;

    @Schema(description = "最近几天有消费")
    private Integer consumeDay;

    @Schema(description = "最低消费次数")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer consumeNum;

    @Schema(description = "最低消费金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer consumeAmount;

    @Schema(description = "注册渠道 PC ANDROID IOS H5 OTHER")
    private Channel channel;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    @OptionInt(value = {0, 1, 2}, required = false, message = "性别参数错误")
    private Integer sex;
}
