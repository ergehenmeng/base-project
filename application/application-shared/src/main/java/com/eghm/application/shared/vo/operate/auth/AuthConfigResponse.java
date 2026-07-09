package com.eghm.application.shared.vo.operate.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
public class AuthConfigResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "单位名称")
    private String title;

    @Schema(description = "appId")
    private String appId;

    @Schema(description = "邮件")
    public String email;

    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
