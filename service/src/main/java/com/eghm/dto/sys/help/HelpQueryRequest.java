package com.eghm.dto.sys.help;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryRequest extends PagingQuery {

    @Schema(description = "帮助说明类型")
    private Integer helpType;

    @Schema(description = "是否显示 0:不显示 1:显示")
    private Integer state;

}
