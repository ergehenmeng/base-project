package com.eghm.dto.member.tag;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class SendSmsRequest {

    @ApiModelProperty("短信模板id")
    @NotBlank(message = "请选择短信模板")
    private String templateId;

    @ApiModelProperty("短信模板参数,逗号分隔")
    private String params;

    @ApiModelProperty("会员id(二选一优先级最高)")
    private List<Long> memberIds;

    @ApiModelProperty("标签id(二选一优先级次之)")
    private Long tagId;

}
