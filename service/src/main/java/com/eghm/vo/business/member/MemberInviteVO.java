package com.eghm.vo.business.member;

import com.eghm.annotation.Desensitization;
import com.eghm.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/18
 */
@Data
public class MemberInviteVO {

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("手机号")
    @Desensitization(FieldType.MOBILE_PHONE)
    private String mobile;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
}
