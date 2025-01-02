package com.eghm.dto.operate.version;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/8/22 15:12
 */
@Setter
@Getter
public class VersionQueryRequest extends PagingQuery {

    @Schema(description = "上架状态 false:待上架 true:已上架")
    private Boolean state;

    @Schema(description = "平台: ANDROID, IOS")
    @OptionString(value = {"ANDROID", "IOS"}, message = "请选择正确的平台", required = false)
    private String channel;

}
