package com.eghm.model.vo.user;

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
public class UserNoticeVO {

    /**
     * 通知id
     */
    @ApiModelProperty(value = "通知id", required = true)
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "通知标题", required = true)
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "通知内容", required = true)
    private String content;

    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型", required = true)
    private String classify;

    /**
     * 已读未读状态
     */
    @ApiModelProperty(value = "已读未读状态 true:已读 false:未读", required = true)
    private Boolean read;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "通知时间yyyy-MM-dd HH:mm:ss", required = true)
    private Date addTime;
}
