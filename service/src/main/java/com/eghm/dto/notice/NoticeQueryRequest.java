package com.eghm.dto.notice;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PagingQuery {

    @ApiModelProperty("公告类型")
    private Integer noticeType;

    @ApiModelProperty("公告状态 0:未发布 1:已发布")
    private Integer state;
}
