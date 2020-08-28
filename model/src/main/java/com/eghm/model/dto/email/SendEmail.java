package com.eghm.model.dto.email;

import com.eghm.common.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmail {

    /**
     * 接收邮件的地址
     */
    private String email;

    /**
     * 邮箱模板类型
     */
    private EmailType type;
}
