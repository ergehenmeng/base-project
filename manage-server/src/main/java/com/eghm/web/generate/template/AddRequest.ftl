package ${template.packageName};

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author 二哥很猛
* @since ${.now?string('yyyy-MM-dd')}
*/
@Data
public class ${template.fileName}AddRequest {

<#list template.fieldList as field>
    @ApiModelProperty(value = "${field.desc}", required = true)
    <#if field.fieldType == "String">
    @NotBlank(message = "${field.desc}不能为空")
    </#if>
    <#if field.fieldType == "Integer" || field.fieldType == "Long" || field.fieldType == "BigDecimal" || field.fieldType == "Boolean" || field.fieldType == "LocalDateTime" || field.fieldType == "LocalDate">
    @NotNull(message = "${field.desc}不能为空")
    </#if>
    private ${field.fieldType} ${field.fieldName};

</#list>
}
