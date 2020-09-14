package com.eghm.model.dto.ext;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 通知类推送
 *
 * @author 二哥很猛
 * @date 2019/8/29 14:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushNotice {

    /**
     * 别名
     */
    private String alias;

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送内容
     */
    private String content;

    /**
     * 通知跳转的页面
     */
    private String viewTag;

    /**
     * 消息通知附加信息
     */
    private final Map<String, String> extras = Maps.newHashMapWithExpectedSize(4);

}
