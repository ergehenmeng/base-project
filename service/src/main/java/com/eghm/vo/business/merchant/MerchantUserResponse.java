package com.eghm.vo.business.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/9/26
 */
@Data
public class MerchantUserResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "联系人姓名")
    private String nickName;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "状态 0:禁用 1:正常")
    private Integer state;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
