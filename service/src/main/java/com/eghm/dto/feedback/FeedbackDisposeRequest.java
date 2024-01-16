package com.eghm.dto.feedback;

import com.eghm.dto.ext.ActionRecord;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 反馈处理
 *
 * @author 二哥很猛
 * @date 2019/8/28 14:26
 */
@Setter
@Getter
public class FeedbackDisposeRequest extends ActionRecord {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "回复信息", required = true)
    @NotBlank(message = "回复信息不能为空")
    @WordChecker(message = "回复信息存在敏感字")
    private String remark;

}
