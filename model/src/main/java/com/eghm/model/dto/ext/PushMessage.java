package com.eghm.model.dto.ext;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 消息类推送
 * @author 二哥很猛
 * @date 2020/9/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage {

    /**
     * 接收消息对象的别名(唯一标示符)
     */
    private String alias;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息通知附加信息
     */
    private final Map<String,String> extras = Maps.newHashMapWithExpectedSize(8);

}
