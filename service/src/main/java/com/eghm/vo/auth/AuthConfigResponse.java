package com.eghm.vo.auth;

import com.eghm.enums.ref.SignType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
public class AuthConfigResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("单位名称")
    private String title;

    @ApiModelProperty("appKey")
    private String appKey;

    @ApiModelProperty("私钥")
    public String privateKey;

    @ApiModelProperty("签名方式")
    private SignType signType;

    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
