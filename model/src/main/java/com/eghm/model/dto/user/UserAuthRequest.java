package com.eghm.model.dto.user;

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
public class UserAuthRequest implements Serializable {

    private static final long serialVersionUID = 7766577359309504055L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String realName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号码")
    @IdCard
    private String idCard;
}
