package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.SysNoticeMapper;
import com.eghm.dao.model.SysNotice;
import com.eghm.model.dto.notice.NoticeAddRequest;
import com.eghm.model.dto.notice.NoticeEditRequest;
import com.eghm.model.dto.notice.NoticeQueryRequest;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.eghm.service.common.SysNoticeService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:41
 */
@Service("sysNoticeService")
@Transactional(rollbackFor = RuntimeException.class)
public class SysNoticeServiceImpl implements SysNoticeService {

    private SysNoticeMapper sysNoticeMapper;

    private SysConfigApi sysConfigApi;

    @Autowired
    public void setSysNoticeMapper(SysNoticeMapper sysNoticeMapper) {
        this.sysNoticeMapper = sysNoticeMapper;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_NOTICE, cacheManager = "smallCacheManager", unless = "#result.size() == 0")
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<TopNoticeVO> getList() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = sysNoticeMapper.getTopList(noticeLimit);
        return DataUtil.convert(noticeList, notice -> DataUtil.copy(notice, TopNoticeVO.class));
    }

    @Override
    public void addNotice(NoticeAddRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.insertSelective(notice);
    }

    @Override
    public void editNotice(NoticeEditRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        SysNotice notice = new SysNotice();
        notice.setId(id);
        notice.setDeleted(true);
        sysNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SysNotice> getByPage(NoticeQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysNotice> list = sysNoticeMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public SysNotice getById(Integer id) {
        return sysNoticeMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_NOTICE, beforeInvocation = true)
    public void publish(Integer id) {
        SysNotice notice = new SysNotice();
        notice.setState((byte) 1);
        notice.setId(id);
        sysNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_NOTICE, beforeInvocation = true)
    public void cancelPublish(Integer id) {
        SysNotice notice = new SysNotice();
        notice.setState((byte) 0);
        notice.setId(id);
        sysNoticeMapper.updateByPrimaryKeySelective(notice);
    }
}
