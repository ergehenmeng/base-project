package com.eghm.model.dto.notice;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = -6968777991245814063L;

    @ApiModelProperty("公告类型")
    private Byte classify;

}
