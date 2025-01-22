package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.Channel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "注册开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerStartDate;

    @Schema(description = "注册截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerEndDate;

    @Schema(description = "最近几天有消费")
    private Integer consumeDay;

    @Schema(description = "最低消费次数")
    private Integer consumeNum;

    @Schema(description = "最低消费金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer consumeAmount;

    @Schema(description = "注册渠道 PC,ANDROID,IOS,H5,OTHER")
    private Channel channel;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    private Integer sex;

    @Schema(description = "符合该标签的会员数")
    private Integer memberNum;

}
