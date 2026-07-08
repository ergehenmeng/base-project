package com.eghm.service.business;

import com.eghm.dto.ext.Page;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.business.statistics.CollectRequest;
import com.eghm.enums.CollectType;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import com.eghm.vo.operate.notice.NoticeVO;

import java.util.List;

/**
 * 会员收藏查询端口
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectQueryGateway {

    Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, CollectQueryDTO query);

    List<CollectStatisticsVO> dayCollect(CollectRequest request);

    boolean existsCollectObject(Long collectId, CollectType collectType);

    List<NewsVO> listNews(List<Long> newsIds);

    List<NoticeVO> listNotice(List<Long> noticeIds);
}
