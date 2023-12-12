package com.eghm.dto.member;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/12/12
 */

@Data
public class MemberDTO {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 6, message = "昵称长度最多6个字符")
    private String nickName;

    @ApiModelProperty("性别")
    @OptionInt(value = {0, 1, 2}, message = "请选择性别")
    private Integer sex;

}
