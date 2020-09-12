package com.eghm.model.ext;

import com.eghm.common.enums.InMailType;
import lombok.Data;

import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@Data
public class SendInMail {

    /**
     * 站内信类型
     */
    private InMailType inMailType;

    /**
     * 参数类型 用于渲染消息模板
     */
    private Map<String, Object> params;

    /**
     * 附加的参数类型 用于前端业务跳转等
     */
    private Map<String, String> extras;
}
