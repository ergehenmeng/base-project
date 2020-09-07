package com.eghm.model.vo.score;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@Data
public class UserScoreVO {

    /**
     * 积分
     */
    @ApiModelProperty("积分值")
    private Integer score;

    /**
     * 积分类型
     */
    @ApiModelProperty("积分类型")
    private Byte type;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("积分发放或消费时间")
    private Date addTime;
}
