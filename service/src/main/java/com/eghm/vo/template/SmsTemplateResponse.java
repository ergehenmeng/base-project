package com.eghm.vo.template;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class SmsTemplateResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("短信模板nid即短信类型")
    private String nid;

    @ApiModelProperty("短信内容")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}