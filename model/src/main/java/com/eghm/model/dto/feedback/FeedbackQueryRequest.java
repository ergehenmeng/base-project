package com.eghm.model.dto.feedback;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/28 13:50
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class FeedbackQueryRequest extends PagingQuery {

    private static final long serialVersionUID = 1054408796616316094L;

    @ApiModelProperty("分类信息(数据字典)")
    private Integer classify;

    @ApiModelProperty("状态 false:待解决 true:已解决")
    private Boolean state;

}
