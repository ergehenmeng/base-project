package com.eghm.dto.notice;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -6968777991245814063L;

    @ApiModelProperty("公告类型")
    private Integer classify;

}
