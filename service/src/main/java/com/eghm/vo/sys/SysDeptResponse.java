package com.eghm.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/8/19
 */

@Data
public class SysDeptResponse {

    @Schema(description = "部门id")
    private Long id;

    @Schema(description = "父级编号")
    private String parentCode;

    @Schema(description = "部门编号")
    private String code;

    @Schema(description = "部门名称")
    private String title;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "子部门")
    private List<SysDeptResponse> children;
}
