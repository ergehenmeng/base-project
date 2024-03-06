package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long title;

    @ApiModelProperty(value = "注册开始日期")
    private LocalDate registerStartDate;

    @ApiModelProperty(value = "注册截止日期")
    private LocalDate registerEndDate;

    @ApiModelProperty(value = "最近几天有消费")
    private Integer consumeDay;

    @ApiModelProperty(value = "最低消费次数")
    private Integer consumeNum;

    @ApiModelProperty(value = "最低消费金额")
    private Integer consumeAmount;

    @ApiModelProperty(value = "符合该标签的会员数")
    private Integer memberNum;

}
