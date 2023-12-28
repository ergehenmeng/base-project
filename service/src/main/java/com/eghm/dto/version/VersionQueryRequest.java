package com.eghm.dto.version;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:12
 */
@Setter
@Getter
public class VersionQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "上架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty(value = "版本类型: ANDROID, IOS")
    private String classify;

}
