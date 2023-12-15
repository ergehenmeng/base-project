package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("notice_template")
public class NoticeTemplate implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("消息模板code")
    private String code;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("更新日期")
    private LocalDateTime updateTime;

}