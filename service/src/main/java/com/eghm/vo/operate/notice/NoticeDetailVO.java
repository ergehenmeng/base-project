package com.eghm.vo.operate.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
public class NoticeDetailVO {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告名称")
    private String title;

    @ApiModelProperty("公告内容(富文本)")
    private String content;

    @ApiModelProperty("公告类型名称")
    private String noticeType;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
