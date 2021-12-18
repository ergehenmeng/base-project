package com.eghm.service.common.impl;

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
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.common.SysBulletinService;
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
@Service("sysBulletinService")
public class SysBulletinServiceImpl implements SysBulletinService {

    private SysBulletinMapper sysBulletinMapper;

    private SysConfigApi sysConfigApi;

    private ProxyService proxyService;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setProxyService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Autowired
    public void setSysBulletinMapper(SysBulletinMapper sysBulletinMapper) {
        this.sysBulletinMapper = sysBulletinMapper;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_BULLETIN, cacheManager = "smallCacheManager", unless = "#result.size() == 0")
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<TopBulletinVO> getList() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysBulletin> noticeList = sysBulletinMapper.getTopList(noticeLimit);
        return DataUtil.convert(noticeList, notice -> {
            TopBulletinVO vo = DataUtil.copy(notice, TopBulletinVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(proxyService.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addNotice(BulletinAddRequest request) {
        SysBulletin notice = DataUtil.copy(request, SysBulletin.class);
        notice.setId(keyGenerator.generateKey());
        sysBulletinMapper.insert(notice);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editNotice(BulletinEditRequest request) {
        SysBulletin notice = DataUtil.copy(request, SysBulletin.class);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteNotice(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setId(id);
        notice.setDeleted(true);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SysBulletin> getByPage(BulletinQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysBulletin> list = sysBulletinMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public SysBulletin getById(Long id) {
        return sysBulletinMapper.selectById(id);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_BULLETIN, beforeInvocation = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public void publish(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setState((byte) 1);
        notice.setId(id);
        sysBulletinMapper.updateById(notice);
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_BULLETIN, beforeInvocation = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelPublish(Long id) {
        SysBulletin notice = new SysBulletin();
        notice.setState(SysBulletin.STATE_0);
        notice.setId(id);
        sysBulletinMapper.updateById(notice);
    }
}
