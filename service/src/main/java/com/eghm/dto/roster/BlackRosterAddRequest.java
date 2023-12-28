package com.eghm.dto.roster;

import com.eghm.convertor.IpToLongDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:53
 */
@Data
public class BlackRosterAddRequest {

    @ApiModelProperty(value = "ip地址开始", required = true)
    @JsonDeserialize(using = IpToLongDecoder.class)
    @NotNull(message = "开始ip地址不能为空")
    private Long startIp;

    @ApiModelProperty(value = "ip地址结束", required = true)
    @JsonDeserialize(using = IpToLongDecoder.class)
    @NotNull(message = "结束ip地址不能为空")
    private Long endIp;

}
