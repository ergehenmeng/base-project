package com.eghm.vo.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公告置顶vo
 * @author 二哥很猛
 * @date 2019/11/25 15:30
 */
@Data
@ApiModel
public class NoticeDetailVO implements Serializable {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告名称")
    private String title;

    @ApiModelProperty("公告内容(富文本)")
    private String content;

    @ApiModelProperty("公告类型名称")
    private String classifyName;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
