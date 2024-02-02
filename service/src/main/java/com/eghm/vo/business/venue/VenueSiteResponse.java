package com.eghm.vo.business.venue;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场地名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private Boolean state;

    @ApiModelProperty(value = "排序 小<->大")
    private Integer sort;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
