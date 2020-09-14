package com.eghm.model.dto.ext;

import com.eghm.common.enums.PushType;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 推送模板类通知
 * @author 殿小二
 * @date 2020/9/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushTemplateNotice {

    /**
     * 模板类型
     */
    private PushType pushType;

    /**
     * 接收消息的用户
     */
    private String alias;

    /**
     * 模板参数
     */
    private Map<String, Object> params;

    /**
     * 推送附加的参数信息
     */
    private final Map<String, String> extras = Maps.newHashMapWithExpectedSize(4);
}
