package com.eghm.dto.operate.roster;

import com.eghm.convertor.IpToLongDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:53
 */
@Data
public class BlackRosterAddRequest {

    @Schema(description = "ip地址开始", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = IpToLongDecoder.class)
    @NotNull(message = "开始ip地址不能为空")
    private Long startIp;

    @Schema(description = "ip地址结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = IpToLongDecoder.class)
    @NotNull(message = "结束ip地址不能为空")
    private Long endIp;

    @Schema(description = "备注信息")
    @Length(max = 100, message = "备注信息最大100字符")
    private String remark;

}
