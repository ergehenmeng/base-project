package com.eghm.state.machine.dto;

import com.eghm.validation.annotation.IdCard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2022/7/27
 */
@Data
public class VisitorDTO {

    @Schema(description = "游客姓名")
    @Size(min = 2, max = 10, message = "游客姓名长度2~10字符")
    @NotBlank(message = "游客姓名不能为空")
    private String memberName;

    @Schema(description = "身份证")
    @IdCard
    private String idCard;

}
