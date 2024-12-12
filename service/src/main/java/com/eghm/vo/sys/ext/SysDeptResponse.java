package com.eghm.vo.sys.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/8/19
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class SysDeptResponse {

    @ApiModelProperty("部门id")
    private Long id;

    @ApiModelProperty("父级编号")
    private String parentCode;

    @ApiModelProperty("部门编号")
    private String code;

    @ApiModelProperty("部门名称")
    private String title;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("子部门")
    private List<SysDeptResponse> children;
}
