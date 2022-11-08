package com.eghm.model.dto.notice;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:15
 */
@Data
public class NoticeAddRequest implements Serializable {

    private static final long serialVersionUID = 3360468576576094581L;

    @ApiModelProperty(value = "公告标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "公告类型(数据字典表sys_notice_type)", required = true)
    @OptionByte(value = {1, 2, 3, 4}, message = "公告类型错误")
    private Byte classify;

    @ApiModelProperty(value = "公告内容(富文本)", required = true)
    @NotBlank(message = "公告内容不能为空")
    private String content;

}
