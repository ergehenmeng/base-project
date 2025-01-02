package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.SignType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Schema(description = "appKey")
    private String appKey;

    @Schema(description = "公钥(不对外暴露)")
    @JsonIgnore
    private String publicKey;

    @Schema(description = "私钥")
    public String privateKey;

    @Schema(description = "签名方式")
    private SignType signType;

    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "备注信息")
    private String remark;
}
