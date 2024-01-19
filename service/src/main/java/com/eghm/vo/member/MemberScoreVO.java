package com.eghm.vo.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Data
public class MemberScoreVO {

    @ApiModelProperty("积分值")
    private Integer score;

    @ApiModelProperty("积分类型")
    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("积分发放或消费时间")
    private Date createTime;
}
