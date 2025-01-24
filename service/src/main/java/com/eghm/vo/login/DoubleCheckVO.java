package com.eghm.vo.login;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/1/24
 */

@Data
@AllArgsConstructor
public class DoubleCheckVO {

    @Schema(description = "手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @Schema(description = "uuid")
    private String uuid;
}
