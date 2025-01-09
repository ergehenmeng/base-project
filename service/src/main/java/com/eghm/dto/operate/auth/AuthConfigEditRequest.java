package com.eghm.dto.operate.auth;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
public class AuthConfigEditRequest {

    @ApiModelProperty(value = "id不能为空", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "单位名称", required = true)
    @Size(min = 2, max = 20, message = "单位名称长度2~20位")
    @NotBlank(message = "单位名称称不能为空")
    private String title;

    @ApiModelProperty("过期时间(默认一年过期)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate expireDate;

    @ApiModelProperty("备注信息")
    private String remark;
}
