package com.eghm.model.dto.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:17
 */
@Data
public class NoticeEditRequest implements Serializable {

    private static final long serialVersionUID = 9017621972387136443L;

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "公告标题", required = true)
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "公告类型(数据字典表sys_notice_type)", required = true)
    private Integer classify;

    @ApiModelProperty(value = "公告内容(富文本)", required = true)
    @NotNull(message = "公告内容不能为空")
    private String content;

}
