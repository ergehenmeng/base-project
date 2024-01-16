package com.eghm.dto.member;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.IdCard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 实名制认证
 *
 * @author 二哥很猛
 * @date 2019/8/28 16:29
 */
@Data
public class MemberAuthDTO {

    @Assign
    @ApiModelProperty(hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "姓名", required = true)
    @NotEmpty(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "身份证号码", required = true)
    @IdCard
    private String idCard;
}
