package com.eghm.model.dto.user;

import com.eghm.model.annotation.Sign;
import com.eghm.model.validation.annotation.IdCard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 实名制认证
 * @author 二哥很猛
 * @date 2019/8/28 16:29
 */
@Data
public class UserAuthDTO implements Serializable {

    private static final long serialVersionUID = 7766577359309504055L;

    @Sign
    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty("身份证号码")
    @IdCard
    private String idCard;
}
