package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.application.shared.vo.operate.feedback.FeedbackResponse;

/**
 * 用户反馈查询端口
 *
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
public interface FeedbackQueryService {

    Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, FeedbackQueryRequest request);
}
