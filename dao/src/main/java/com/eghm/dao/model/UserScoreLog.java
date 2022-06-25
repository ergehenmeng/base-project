package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName("user_score_log")
public class UserScoreLog extends BaseEntity {

    @ApiModelProperty("用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty("本次收入或支出的积分数")
    private Integer score;

    @ApiModelProperty("积分收入或支出分类")
    private Byte type;

    @ApiModelProperty("备注信息")
    private String remark;

}