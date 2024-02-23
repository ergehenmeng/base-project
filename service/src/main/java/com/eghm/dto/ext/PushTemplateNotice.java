package com.eghm.dto.ext;

import com.eghm.enums.PushType;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 推送模板类通知
 *
 * @author 殿小二
 * @since 2020/9/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushTemplateNotice {

    @ApiModelProperty("推送附加的参数信息")
    private final Map<String, String> extras = Maps.newHashMapWithExpectedSize(4);

    @ApiModelProperty("推送类型")
    private PushType pushType;

    @ApiModelProperty("接收者别名")
    private String alias;

    @ApiModelProperty("模板参数")
    private Map<String, Object> params;
}
