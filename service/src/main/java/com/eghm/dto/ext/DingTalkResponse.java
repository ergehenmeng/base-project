package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkResponse {

    @ApiModelProperty("响应状态码 0:成功")
    @JsonProperty("errcode")
    private Integer errCode = -1;

    @ApiModelProperty("错误信息")
    @JsonProperty("errmsg")
    private String errMsg = "响应参数解析异常";
}
