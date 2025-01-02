package com.eghm.dto.operate.auth;

import com.eghm.enums.SignType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Schema(description = "签名方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "签名方式不能为空")
    private SignType signType;

    @Schema(description = "过期时间(默认一年)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "备注信息")
    private String remark;
}
