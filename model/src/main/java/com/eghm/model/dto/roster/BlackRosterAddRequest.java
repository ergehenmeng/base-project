package com.eghm.model.dto.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:53
 */
@Data
public class BlackRosterAddRequest implements Serializable {

    private static final long serialVersionUID = 8774607401819334344L;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("黑名单截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
