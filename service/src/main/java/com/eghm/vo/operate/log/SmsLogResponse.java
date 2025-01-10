package com.eghm.vo.operate.log;

import com.eghm.enums.TemplateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class SmsLogResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "短信分类 default:自定义 member_login:登陆 user_login:登陆(后台), forget:忘记密码, register:注册, change_email:更换邮箱")
    private TemplateType templateType;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "短信内容")
    private String content;

    @Schema(description = "发送状态 0:失败 1:已发送")
    private Integer state;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
