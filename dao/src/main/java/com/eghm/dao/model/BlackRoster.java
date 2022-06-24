package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("black_roster")
public class BlackRoster extends BaseEntity {

    @ApiModelProperty("访问ip")
    private String ip;

    @ApiModelProperty(value = "数字ip", hidden = true)
    private Long longIp;

    @ApiModelProperty("黑名单截止时间")
    private Date endTime;

}