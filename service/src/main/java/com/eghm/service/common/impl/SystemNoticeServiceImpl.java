package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.common.SystemNoticeMapper;
import com.eghm.dao.model.business.SystemNotice;
import com.eghm.model.dto.business.notice.NoticeAddRequest;
import com.eghm.model.dto.business.notice.NoticeEditRequest;
import com.eghm.model.dto.business.notice.NoticeQueryRequest;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.eghm.service.common.SystemNoticeService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@Service("systemNoticeService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemNoticeServiceImpl implements SystemNoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYSTEM_NOTICE, cacheManager = "smallCacheManager", unless = "#result.size() == 0")
    public List<TopNoticeVO> getList() {
        int noticeLimit = systemConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SystemNotice> noticeList = systemNoticeMapper.getTopList(noticeLimit);
        return DataUtil.convert(noticeList, notice -> DataUtil.copy(notice, TopNoticeVO.class));
    }

    @Override
    public void addNotice(NoticeAddRequest request) {
        SystemNotice notice = DataUtil.copy(request, SystemNotice.class);
        systemNoticeMapper.insertSelective(notice);
    }

    @Override
    public void editNotice(NoticeEditRequest request) {
        SystemNotice notice = DataUtil.copy(request, SystemNotice.class);
        systemNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        SystemNotice notice = new SystemNotice();
        notice.setId(id);
        notice.setDeleted(true);
        systemNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public PageInfo<SystemNotice> getByPage(NoticeQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<SystemNotice> list = systemNoticeMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public SystemNotice getById(Integer id) {
        return systemNoticeMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYSTEM_NOTICE, beforeInvocation = true)
    public void publish(Integer id) {
        SystemNotice notice = new SystemNotice();
        notice.setState((byte) 1);
        notice.setId(id);
        systemNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYSTEM_NOTICE, beforeInvocation = true)
    public void cancelPublish(Integer id) {
        SystemNotice notice = new SystemNotice();
        notice.setState((byte) 0);
        notice.setId(id);
        systemNoticeMapper.updateByPrimaryKeySelective(notice);
    }
}
