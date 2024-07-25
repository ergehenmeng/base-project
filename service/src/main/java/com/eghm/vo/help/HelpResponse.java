package com.eghm.vo.help;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class HelpResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("帮助分类取sys_dict表中help_type字段")
    private Integer helpType;

    @ApiModelProperty("状态 0:不显示 1:显示")
    private Integer state;

    @ApiModelProperty("问")
    private String ask;

    @ApiModelProperty("排序(小<->大)")
    private Integer sort;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
