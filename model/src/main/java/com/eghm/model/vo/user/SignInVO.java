package com.eghm.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@Data
public class SignInVO {

    /**
     * 累计签到天数
     */
    @ApiModelProperty("今日是否签到 true:已签到 false:未签到")
    private Integer addUp;

    /**
     * 今日是否签到
     */
    @ApiModelProperty("今日是否签到 true:已签到 false:未签到")
    private Boolean today;

    /**
     * 本月签到信息1号~昨日
     */
    @ApiModelProperty("本月签到信息")
    private List<Boolean> thisMonth;

}
