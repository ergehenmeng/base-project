package com.eghm.dto.ext;

import com.eghm.enums.NoticeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 发送用户站内信
 *
 * @author 殿小二
 * @since 2020/9/12
 */
@Data
public class SendNotice {

    @ApiModelProperty("站内信类型")
    private NoticeType noticeType;

    @ApiModelProperty("参数类型 用于渲染消息模板")
    private Map<String, Object> params;

    @ApiModelProperty("附加的参数类型 用于前端业务跳转等")
    private Map<String, String> extras;
}
