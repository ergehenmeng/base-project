package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.comment.CommentQueryDTO;
import com.eghm.application.shared.dto.operate.comment.CommentQueryRequest;
import com.eghm.domain.operate.model.News;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.operate.comment.CommentResponse;
import com.eghm.application.shared.vo.operate.comment.CommentSecondVO;
import com.eghm.application.shared.vo.operate.comment.CommentVO;

import java.util.List;
import java.util.Map;

/**
 * 评论查询端口
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface CommentQueryService {

    Page<CommentResponse> listPage(Page<CommentResponse> page, CommentQueryRequest request);

    Page<CommentVO> getByPage(CommentQueryDTO dto, Integer reportNum);

    Page<CommentSecondVO> getSecondPage(CommentQueryDTO dto, Integer reportNum);

    List<Long> listNewsIdsByTitle(String queryName);

    Map<Long, String> getNewsTitleMap(List<Long> newsIds);

    News findNewsById(Long id);
}
