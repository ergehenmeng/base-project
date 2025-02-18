package com.eghm.vo.business.scenic;

import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/6/3
 */

@Data
public class BaseScenicResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "景区名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

}
