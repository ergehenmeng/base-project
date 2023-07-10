package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("black_roster")
public class BlackRoster extends BaseEntity {

    @ApiModelProperty("访问ip")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String ip;

    @ApiModelProperty(value = "数字ip", hidden = true)
    private Long longIp;

    @ApiModelProperty("黑名单截止时间")
    private LocalDateTime endTime;

}