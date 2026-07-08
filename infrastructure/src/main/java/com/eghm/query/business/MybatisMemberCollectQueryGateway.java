package com.eghm.query.business;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.business.statistics.CollectRequest;
import com.eghm.enums.CollectType;
import com.eghm.mapper.MemberCollectMapper;
import com.eghm.mapper.NewsMapper;
import com.eghm.mapper.SysNoticeMapper;
import com.eghm.service.business.MemberCollectQueryGateway;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import com.eghm.vo.operate.notice.NoticeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.eghm.enums.CollectType.NEWS;

@Repository
@AllArgsConstructor
public class MybatisMemberCollectQueryGateway implements MemberCollectQueryGateway {

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





