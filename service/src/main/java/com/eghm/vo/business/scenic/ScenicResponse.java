package com.eghm.vo.business.scenic;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/3
 */

@Data
public class ScenicResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "景区名称")
    private String scenicName;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3: 3A 0:其他")
    private Integer level;

    @ApiModelProperty(value = "景区营业时间")
    private String openTime;

    @ApiModelProperty("景区电话")
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "景区排序(小<->大)")
    private Integer sort;

    @ApiModelProperty(value = "景区图片")
    private String coverUrl;

    @ApiModelProperty(value = "最低票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty("最高票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;

    @ApiModelProperty("景区评分")
    private Integer score;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
