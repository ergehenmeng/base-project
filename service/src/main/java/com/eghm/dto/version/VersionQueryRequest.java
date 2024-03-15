package com.eghm.dto.version;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/8/22 15:12
 */
@Setter
@Getter
public class VersionQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "上架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty(value = "平台: ANDROID, IOS")
    @OptionString(value = {"ANDROID", "IOS"}, message = "请选择正确的平台", required = false)
    private String channel;

}
