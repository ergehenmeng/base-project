package com.eghm.vo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@Data
public class SignInVO {

    @Schema(description = "今日是否签到 true:已签到 false:未签到")
    private Boolean todaySign;

    @Schema(description = "本月签到信息(1号~今日)")
    private List<Boolean> monthSign;

}
