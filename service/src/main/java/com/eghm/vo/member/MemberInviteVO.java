package com.eghm.vo.member;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/18
 */
@Data
public class MemberInviteVO {

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
}
