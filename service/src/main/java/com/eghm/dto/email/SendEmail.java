package com.eghm.dto.email;

import com.eghm.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 为减少不必要的代码污染, 禁止在该类中添加其他参数,如需传递参数,可调用put方法
 *
 * @author 殿小二
 * @since 2020/8/28
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

    /**
     * 附加信息 可以用于渲染模板,也可以用作参数的传递
     */
    private Map<String, Object> params;

    /**
     * 添加参数信息
     *
     * @param key   key
     * @param value value
     */
    public void put(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }
}
