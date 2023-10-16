package com.eghm.vo.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@Data
@ApiModel
public class MemberNoticeVO {

    @ApiModelProperty(value = "通知id", required = true)
    private Long id;

    @ApiModelProperty(value = "通知标题", required = true)
    private String title;

    @ApiModelProperty(value = "通知内容", required = true)
    private String content;

    @ApiModelProperty(value = "通知类型", required = true)
    private String classify;

    @ApiModelProperty(value = "已读未读状态 true:已读 false:未读", required = true)
    private Boolean isRead;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "通知时间yyyy-MM-dd HH:mm:ss", required = true)
    private Date createTime;
}
