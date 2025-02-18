package com.eghm.dto.operate.roster;

import com.eghm.convertor.IpToLongDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:53
 */
@Data
public class BlackRosterAddRequest {

    @ApiModelProperty(value = "ip地址开始", required = true)
    @JsonDeserialize(using = IpToLongDeserializer.class)
    @NotNull(message = "开始ip地址不能为空")
    private Long startIp;

    @ApiModelProperty(value = "ip地址结束", required = true)
    @JsonDeserialize(using = IpToLongDeserializer.class)
    @NotNull(message = "结束ip地址不能为空")
    private Long endIp;

    @ApiModelProperty("备注信息")
    @Length(max = 100, message = "备注信息最大100字符")
    private String remark;

}
