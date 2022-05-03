package com.eghm.model.dto.notice;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:15
 */
@Data
public class NoticeAddRequest implements Serializable {

    private static final long serialVersionUID = 3360468576576094581L;

    /**
     * 公告标题
     */
    @ApiModelProperty(value = "公告标题", required = true)
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 公告类型(数据字典表sys_notice_type) 类型待定
     */
    @ApiModelProperty(value = "公告类型(数据字典表sys_notice_type)", required = true)
    @OptionByte(value = {1, 2, 3, 4}, message = "公告类型错误")
    private Byte classify;

    /**
     * 公告内容(富文本)
     */
    @ApiModelProperty(value = "公告内容(富文本)", required = true)
    @NotNull(message = "公告内容不能为空")
    private String content;

    /**
     * 原始内容
     */
    @ApiModelProperty(value = "公告内容(原始内容)", required = true)
    @NotNull(message = "公告原始内容不能为空")
    private String originalContent;
}
