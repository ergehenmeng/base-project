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

    @ApiModelProperty("短信内容")
    @NotBlank(message = "短信内容不能为空")
    @Size(min = 10, max = 70, message = "站内信内容长度10~70位")
    @WordChecker(message = "短信内容存在敏感词")
    private String content;

    @ApiModelProperty("会员id(二选一优先级最高)")
    private List<Long> memberIds;

    @ApiModelProperty("标签id(二选一优先级次之)")
    private Long tagId;

}
