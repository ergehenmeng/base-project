package com.eghm.dto.feedback;

import com.eghm.dto.ext.ActionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 反馈处理
 * @author 二哥很猛
 * @date 2019/8/28 14:26
 */
@Setter
@Getter
public class FeedbackDisposeRequest extends ActionRecord {

    private static final long serialVersionUID = -7275206427146713271L;

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "备注信息", required = true)
    @NotBlank(message = "备注信息不能为空")
    private String remark;

}
