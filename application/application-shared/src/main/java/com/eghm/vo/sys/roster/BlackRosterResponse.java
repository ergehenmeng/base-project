package com.eghm.vo.sys.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访问黑名单响应
 *
 * @author 二哥很猛
 */
@Data
public class BlackRosterResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "开始ip")
    private Long startIp;

    @Schema(description = "结束ip")
    private Long endIp;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
