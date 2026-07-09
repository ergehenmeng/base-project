package com.eghm.infrastructure.persistence.mybatis.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.collect.CollectQueryDTO;
import com.eghm.application.shared.dto.business.statistics.CollectRequest;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberCollectMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysNoticeMapper;
import com.eghm.application.member.query.MemberCollectQueryService;
import com.eghm.application.shared.vo.business.collect.MemberCollectVO;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.business.statistics.CollectStatisticsVO;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.eghm.enums.CollectType.NEWS;

@Repository
@AllArgsConstructor
public class MybatisMemberCollectQueryService implements MemberCollectQueryService {

    private final NewsMapper newsMapper;

    private final SysNoticeMapper sysNoticeMapper;

    private final MemberCollectMapper memberCollectMapper;

    @Override
    public Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, CollectQueryDTO query) {
        return MybatisPageUtil.fromMybatis(memberCollectMapper.getByPage(MybatisPageUtil.toMybatis(page), query));
    }

    @Override
    public List<CollectStatisticsVO> dayCollect(CollectRequest request) {
        return memberCollectMapper.dayCollect(request);
    }

    @Override
    public boolean existsCollectObject(Long collectId, CollectType collectType) {
        if (collectType == NEWS) {
            return newsMapper.selectById(collectId) != null;
    }
        if (collectType == CollectType.NOTICE) {
            return sysNoticeMapper.selectById(collectId) != null;
    }
        return false;
    }

    @Override
    public List<NewsVO> listNews(List<Long> newsIds) {
        return newsMapper.getList(newsIds);
    }

    @Override
    public List<NoticeVO> listNotice(List<Long> noticeIds) {
        return sysNoticeMapper.getList(noticeIds);
    }
}





