package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysNoticeMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysNoticePO;
import com.eghm.application.operate.service.SysNoticeQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.notice.NoticeResponse;
import com.eghm.vo.operate.notice.NoticeVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MybatisSysNoticeQueryGateway implements SysNoticeQueryGateway {

    private final SysNoticeMapper sysNoticeMapper;

    @Override
    public Page<NoticeResponse> getByPage(Page<NoticeResponse> page, NoticeQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysNoticeMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public List<NoticeVO> getList(PagingQuery query) {
        LambdaQueryWrapper<SysNoticePO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysNoticePO::getId, SysNoticePO::getTitle, SysNoticePO::getCoverUrl, SysNoticePO::getNoticeType);
        wrapper.eq(SysNoticePO::getState, true);
        wrapper.orderByDesc(SysNoticePO::getId);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysNoticePO> selectedPage = sysNoticeMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage(false)), wrapper);
        return DataUtil.copy(selectedPage.getRecords(), NoticeVO.class);
    }
}





