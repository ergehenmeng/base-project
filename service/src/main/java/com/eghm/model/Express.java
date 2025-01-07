package com.eghm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Express extends BaseEntity {

    @Schema(description = "快递公司编码")
    private String expressCode;

    @Schema(description = "快递公司名称")
    private String expressName;
}
