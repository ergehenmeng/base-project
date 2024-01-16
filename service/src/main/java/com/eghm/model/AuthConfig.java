package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.SignType;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("私钥")
    public String privateKey;
    @ApiModelProperty("单位名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;
    @ApiModelProperty("appKey")
    private String appKey;
    @ApiModelProperty("公钥(不对外暴露)")
    @JsonIgnore
    private String publicKey;
    @ApiModelProperty("签名方式")
    private SignType signType;

    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty("备注信息")
    private String remark;
}
