package com.eghm.vo.business.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/9/26
 */
@Data
public class MerchantUserResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "联系人姓名")
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty("状态 0:禁用 1:正常")
    private Integer state;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
