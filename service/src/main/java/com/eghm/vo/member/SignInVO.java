package com.eghm.vo.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@Data
@ApiModel
public class SignInVO {

    @ApiModelProperty("今日是否签到 true:已签到 false:未签到")
    private Boolean todaySign;

    @ApiModelProperty("本月签到信息(1号~今日)")
    private List<Boolean> monthSign;

}
