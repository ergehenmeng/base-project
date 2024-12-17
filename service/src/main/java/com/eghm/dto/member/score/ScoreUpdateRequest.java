package com.eghm.dto.member.score;

import com.eghm.enums.ScoreType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/12/17
 */

@Data
public class ScoreUpdateRequest {

    @ApiModelProperty("会员id")
    @NotNull(message = "会员id不能为空")
    private Long id;

    @ApiModelProperty("积分类型")
    @NotNull(message = "请选择积分类型")
    private ScoreType scoreType;

    @ApiModelProperty("积分数")
    @NotNull(message = "请输入积分数")
    @Max(value = 999, message = "积分不能超过999")
    @Min(value = 1, message = "积分不能小于1")
    private Integer score;

    @ApiModelProperty("积分备注")
    @Length(max = 50, message = "备注不能超过50个字符")
    private String remark;
}
