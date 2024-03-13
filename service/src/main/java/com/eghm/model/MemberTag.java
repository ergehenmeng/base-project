package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.Channel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 会员标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_tag")
public class MemberTag extends BaseEntity {

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "注册开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerStartDate;

    @ApiModelProperty(value = "注册截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerEndDate;

    @ApiModelProperty(value = "最近几天有消费")
    private Integer consumeDay;

    @ApiModelProperty(value = "最低消费次数")
    private Integer consumeNum;

    @ApiModelProperty(value = "最低消费金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer consumeAmount;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,OTHER")
    private Channel channel;

    @ApiModelProperty("性别 0:未知 1:男 2:女 ")
    private Integer sex;

    @ApiModelProperty(value = "符合该标签的会员数")
    private Integer memberNum;

}
