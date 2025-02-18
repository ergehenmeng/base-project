package com.eghm.vo.login;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/1/24
 */

@Data
@AllArgsConstructor
public class DoubleCheckVO {

    @ApiModelProperty(value = "手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty(value = "uuid")
    private String uuid;
}
