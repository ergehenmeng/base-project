package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@TableName("auth_config")
@EqualsAndHashCode(callSuper = true)
public class AuthConfig extends BaseEntity {

    @Schema(description = "单位名称")
    private String title;

    @Schema(description = "appId")
    private String appId;

    @Schema(description = "秘钥")
    private String appSecret;

    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "备注信息")
    private String remark;
}
