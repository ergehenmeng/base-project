package com.eghm.dto.sys.dict;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictQueryRequest extends PagingQuery {

    @Schema(description = "字典分类: 1:系统字典 2:业务字典")
    private Integer dictType;
}
