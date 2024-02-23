package com.eghm.dto.notice;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:15
 */
@Data
public class NoticeAddRequest {

    @ApiModelProperty(value = "公告标题", required = true)
    @NotBlank(message = "标题不能为空")
    @WordChecker(message = "标题存在敏感词")
    private String title;

    @ApiModelProperty(value = "公告类型(数据字典表notice_classify)", required = true)
    @NotNull(message = "公告类型不能为空")
    private Integer classify;

    @ApiModelProperty(value = "公告内容(富文本)", required = true)
    @NotBlank(message = "公告内容不能为空")
    @WordChecker(message = "公告内容存在敏感词")
    private String content;

}
