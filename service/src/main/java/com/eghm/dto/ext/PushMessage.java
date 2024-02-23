package com.eghm.dto.ext;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 消息类推送
 *
 * @author 二哥很猛
 * @since 2020/9/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage {

    @ApiModelProperty("消息通知附加信息")
    private final Map<String, String> extras = Maps.newHashMapWithExpectedSize(8);

    @ApiModelProperty("接收消息对象的别名(唯一标示符)")
    private String alias;

    @ApiModelProperty("消息内容")
    private String content;

}
