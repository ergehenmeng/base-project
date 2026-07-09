package com.eghm.application.shared.dto.operate.auth;

import com.eghm.application.shared.configuration.gson.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
public class AuthConfigAddRequest {

    @Schema(description = "单位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "单位名称长度2~20位")
    @NotBlank(message = "单位名称称不能为空")
    private String title;

    @Schema(description = "过期时间(默认一年)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate expireDate;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息最大100字符")
    private String remark;
}
