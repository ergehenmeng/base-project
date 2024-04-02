package com.eghm.vo.business.item.express;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
public class ExpressTemplateResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "模板名称")
    private String title;

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    private Integer state;

    @ApiModelProperty(value = "计费方式 1:按件数 2:按重量")
    private Integer chargeMode;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("区域价格配置列表")
    private List<ExpressRegionResponse> regionList;
}
