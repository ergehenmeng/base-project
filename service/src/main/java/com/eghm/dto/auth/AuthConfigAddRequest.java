package com.eghm.dto.auth;

import com.eghm.enums.ref.SignType;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
public class AuthConfigAddRequest {

    @ApiModelProperty("单位名称")
    @Size(min = 2, max = 20, message = "单位名称长度2~20位")
    @NotBlank(message = "单位名称称不能为空")
    @WordChecker
    private String title;

    @ApiModelProperty("签名方式")
    @NotBlank(message = "签名方式不能为空")
    private SignType signType;

    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty("备注信息")
    private String remark;
}
