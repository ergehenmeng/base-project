package com.fanyin.model.ext;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/8/29 14:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushBuilder {

    /**
     * 推送模板nid
     */
    private String nid;

    /**
     * 别名
     */
    private String alias;

    /**
     * 推送标题
     */
    private String alert;

    /**
     * 消息通知附加信息
     */
    private Map<String,String> extras = Maps.newHashMapWithExpectedSize(4);
}
