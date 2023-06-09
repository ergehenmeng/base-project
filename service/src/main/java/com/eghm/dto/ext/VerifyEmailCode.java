package com.eghm.dto.ext;

import com.eghm.enums.EmailType;
import lombok.Data;

/**
 * 用于校验用户输入的邮箱验证码是否合法, 该类仅仅作为参数传递的dto类
 * @author 殿小二
 * @date 2020/9/4
 */
@Data
public class VerifyEmailCode {

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱验证码
     */
    private String authCode;

    /**
     * 验证码邮件类型
     */
    private EmailType emailType;
}
