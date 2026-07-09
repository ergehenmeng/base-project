package com.eghm.vo.sys.cache;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统缓存响应
 *
 * @author 二哥很猛
 */
@Data
public class SysCacheResponse {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "缓存标题")
    private String title;

    @Schema(description = "缓存名称")
    private String cacheName;

    @Schema(description = "缓存更新状态 0:未更新 1:更新成功 2:更新失败")
    private Integer state;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "备注说明")
    private String remark;
}
