package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.collect.CollectQueryDTO;
import com.eghm.application.shared.dto.business.statistics.CollectRequest;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.application.shared.vo.business.collect.MemberCollectVO;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.business.statistics.CollectStatisticsVO;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;

import java.util.List;

/**
 * 会员收藏查询端口
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectQueryService {

    Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, CollectQueryDTO query);

    List<CollectStatisticsVO> dayCollect(CollectRequest request);

    boolean existsCollectObject(Long collectId, CollectType collectType);

    List<NewsVO> listNews(List<Long> newsIds);

    List<NoticeVO> listNotice(List<Long> noticeIds);
}
