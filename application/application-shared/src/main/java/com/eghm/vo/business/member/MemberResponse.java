package com.eghm.vo.business.member;

import com.eghm.domain.shared.enums.Gender;
import com.eghm.domain.shared.enums.MemberState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/19
 */
@Data
public class MemberResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "状态 false:注销 true:正常")
    private MemberState state;

    @Schema(description = "总积分数")
    private Integer score;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    private Gender sex;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "生日yyyyMMdd")
    private String birthday;

    @Schema(description = "注册渠道 PC, ANDROID, IOS, H5, OTHER")
    private String channel;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
