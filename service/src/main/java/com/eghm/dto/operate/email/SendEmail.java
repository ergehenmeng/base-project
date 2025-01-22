package com.eghm.dto.operate.email;

import com.eghm.enums.EmailType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "接收邮件的地址")
    private String to;

    @Schema(description = "邮箱模板类型")
    private EmailType type;

    @Schema(description = "附加信息 可以用于渲染模板,也可以用作参数的传递")
    private Map<String, Object> params;

    /**
     * 添加参数信息
     *
     * @param key   key
     * @param value value
     */
    public void addParam(String key, Object value) {
        if (params == null) {
            params = new HashMap<>(8);
        }
        params.put(key, value);
    }
}
