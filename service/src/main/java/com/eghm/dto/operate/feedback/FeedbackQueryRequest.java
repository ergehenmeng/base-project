package com.eghm.dto.operate.feedback;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.FeedbackType;
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

    @Schema(description = "分类 1:功能异常 2:产品建议 3:安全问题 4:其他问题")
    private FeedbackType feedbackType;

    @Schema(description = "状态 false:待解决 true:已解决")
    private Boolean state;

}
