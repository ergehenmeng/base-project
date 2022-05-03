package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.dao.mapper.SysBulletinMapper;
import com.eghm.dao.model.SysBulletin;
import com.eghm.model.dto.bulletin.BulletinAddRequest;
import com.eghm.model.dto.bulletin.BulletinEditRequest;
import com.eghm.model.dto.bulletin.BulletinQueryRequest;
import com.eghm.model.vo.bulletin.TopBulletinVO;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.common.SysBulletinService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:41
 */
@Service("sysBulletinService")
@AllArgsConstructor
public class SysBulletinServiceImpl implements SysBulletinService {

    private final SysBulletinMapper sysBulletinMapper;

    private final SysConfigApi sysConfigApi;

    private final ProxyService proxyService;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_BULLETIN, cacheManager = "smallCacheManager", unless = "#result.size() == 0")
    public List<TopBulletinVO> getList() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysBulletin> noticeList = sysBulletinMapper.getTopList(noticeLimit);
        ProxyService finalProxy = this.proxyService;
        return DataUtil.convert(noticeList, notice -> {
            TopBulletinVO vo = DataUtil.copy(notice, TopBulletinVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(finalProxy.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    public void addNotice(BulletinAddRequest request) {
        SysBulletin notice = DataUtil.copy(request, SysBulletin.class);
        sysBulletinMapper.insert(notice);
    }

    @Override
    public void editNotice(BulletinEditRequest request) {
        SysBulletin notice = DataUtil.copy(request, SysBulletin.class);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setId(id);
        notice.setDeleted(true);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    public Page<SysBulletin> getByPage(BulletinQueryRequest request) {
        LambdaQueryWrapper<SysBulletin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysBulletin::getDeleted, false);
        wrapper.eq(request.getClassify() != null, SysBulletin::getClassify, request.getClassify());
        wrapper.last("order by update_time desc, id desc ");
        return sysBulletinMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysBulletin getById(Long id) {
        return sysBulletinMapper.selectById(id);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_BULLETIN, beforeInvocation = true)
    public void publish(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setState((byte) 1);
        notice.setId(id);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_BULLETIN, beforeInvocation = true)
    public void cancelPublish(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setState(SysBulletin.STATE_0);
        notice.setId(id);
        sysBulletinMapper.updateById(notice);
    }
}
