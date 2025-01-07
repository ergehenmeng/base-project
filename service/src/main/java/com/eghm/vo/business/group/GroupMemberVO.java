package com.eghm.vo.business.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class GroupMemberVO {

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "是否为团长")
    private Boolean starter;

    @Schema(description = "拼团id")
    @JsonIgnore
    private Long bookingId;
}
