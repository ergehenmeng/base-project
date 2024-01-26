package com.eghm.vo.business.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class GroupMemberVO {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("是否为团长")
    private Boolean starter;

    @ApiModelProperty("拼团id")
    @JsonIgnore
    private Long bookingId;
}
