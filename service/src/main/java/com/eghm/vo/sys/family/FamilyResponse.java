package com.eghm.vo.sys.family;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/12/16
 */
@Data
public class FamilyResponse {

    @Schema(description = "主键id")
    private String id;

    @Schema(description = "父节点")
    private String pid;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Schema(description = "是否绝户 0: 未绝户 1: 已绝户")
    private Boolean state;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    @Schema(description = "子女")
    private List<FamilyResponse> children;
}
