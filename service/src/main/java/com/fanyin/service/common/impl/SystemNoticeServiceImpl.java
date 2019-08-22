package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.dao.mapper.common.SystemNoticeMapper;
import com.fanyin.dao.model.business.SystemNotice;
import com.fanyin.model.dto.business.notice.NoticeAddRequest;
import com.fanyin.model.dto.business.notice.NoticeEditRequest;
import com.fanyin.model.dto.business.notice.NoticeQueryRequest;
import com.fanyin.service.common.SystemNoticeService;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:41
 */
@Service("systemNoticeService")
public class SystemNoticeServiceImpl implements SystemNoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYSTEM_NOTICE,cacheManager = "smallCacheManager")
    public List<SystemNotice> getList() {
        int noticeLimit = systemConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        return systemNoticeMapper.getTopList(noticeLimit);
    }

    @Override
    public void addNotice(NoticeAddRequest request) {
        SystemNotice notice = DataUtil.copy(request, SystemNotice.class);
        systemNoticeMapper.insertSelective(notice);
    }

    @Override
    public void updateNotice(NoticeEditRequest request) {
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
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<SystemNotice> list = systemNoticeMapper.getList(request);
        return new PageInfo<>(list);
    }
}
