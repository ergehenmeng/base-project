package com.eghm.dto.operate.feedback;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/8/28 13:50
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class FeedbackQueryRequest extends PagingQuery {

    @Schema(description = "分类信息(数据字典)")
    private Integer feedbackType;

    @Schema(description = "状态 false:待解决 true:已解决")
    private Boolean state;

}
