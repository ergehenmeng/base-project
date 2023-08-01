package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("member_score_log")
public class MemberScoreLog extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("本次收入或支出的积分数")
    private Integer score;

    @ApiModelProperty("积分收入或支出分类")
    private Integer type;

    @ApiModelProperty("备注信息")
    private String remark;

}