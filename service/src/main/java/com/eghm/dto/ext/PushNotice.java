package com.eghm.dto.ext;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 通知类推送
 *
 * @author 二哥很猛
 * @since 2019/8/29 14:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushNotice {

    @ApiModelProperty("消息通知附加信息")
    private final Map<String, String> extras = Maps.newHashMapWithExpectedSize(4);

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息跳转地址")
    private String viewTag;

}
