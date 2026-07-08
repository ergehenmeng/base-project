package com.eghm.cache.impl;

import com.eghm.cache.CacheProxyService;
import com.eghm.constants.CacheConstant;
import com.eghm.enums.Channel;
import com.eghm.enums.EmailType;
import com.eghm.mapper.AuthConfigMapper;
import com.eghm.mapper.BannerMapper;
import com.eghm.mapper.EmailTemplateMapper;
import com.eghm.mapper.NoticeTemplateMapper;
import com.eghm.mapper.SysAreaMapper;
import com.eghm.mapper.SysConfigMapper;
import com.eghm.mapper.SysDictItemMapper;
import com.eghm.mapper.SysNoticeMapper;
import com.eghm.operate.model.EmailTemplate;
import com.eghm.operate.model.NoticeTemplate;
import com.eghm.po.EmailTemplatePO;
import com.eghm.po.NoticeTemplatePO;
import com.eghm.po.SysAreaPO;
import com.eghm.po.SysConfigPO;
import com.eghm.po.SysDictItemPO;
import com.eghm.po.SysNoticePO;
import com.eghm.sys.model.SysArea;
import com.eghm.sys.model.SysDictItem;
import com.eghm.operate.model.SysNotice;
import com.eghm.utils.DataUtil;
import com.eghm.utils.MybatisUtil;
import com.eghm.vo.operate.auth.AuthConfigVO;
import com.eghm.vo.operate.banner.BannerVO;
import com.eghm.vo.sys.ext.SysAreaVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存代理层<br/>
 * 防止循环依赖
 *
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Slf4j
@AllArgsConstructor
@Service("cacheProxyService")
public class CacheProxyServiceImpl implements CacheProxyService {

    private final BannerMapper bannerMapper;

    private final SysAreaMapper sysAreaMapper;

    private final SysNoticeMapper sysNoticeMapper;

    private final SysConfigMapper sysConfigMapper;

    private final AuthConfigMapper authConfigMapper;

    private final SysDictItemMapper sysDictItemMapper;

    private final EmailTemplateMapper emailTemplateMapper;

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_AREA, unless = "#result.size() == 0")
    public List<SysAreaVO> getAreaList() {
        return sysAreaMapper.getList(null);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_AREA_ID, key = "#p0", unless = "#result == null")
    public SysArea getAreaById(Long id) {
        return DataUtil.copy(sysAreaMapper.selectById(id), SysArea.class);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BANNER, key = "#channel.name() + #p1", unless = "#result.size() == 0", cacheManager = "longCacheManager")
    public List<BannerVO> getBanner(Channel channel, Integer bannerType) {
        return bannerMapper.getBannerList(channel.name(), bannerType);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.EMAIL_TEMPLATE, key = "#code.value", unless = "#result == null", cacheManager = "longCacheManager")
    public EmailTemplate getEmailTemplate(EmailType code) {
        return DataUtil.copy(MybatisUtil.getOne(emailTemplateMapper, EmailTemplatePO::getNid, code.name()), EmailTemplate.class);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.IN_MAIL_TEMPLATE, key = "#p0", unless = "#result == null", cacheManager = "longCacheManager")
    public NoticeTemplate getNoticeTemplate(String code) {
        return DataUtil.copy(MybatisUtil.getOne(noticeTemplateMapper, NoticeTemplatePO::getCode, code), NoticeTemplate.class);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_NOTICE, unless = "#result.size() == 0", cacheManager = "longCacheManager")
    public List<SysNotice> getNoticeList(int limit) {
        List<SysNoticePO> list = sysNoticeMapper.getTopList(limit);
        return DataUtil.copy(list, SysNotice.class);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_CONFIG, key = "#p0", unless = "#result == null", cacheManager = "longCacheManager")
    public String getConfigByNid(String nid) {
        SysConfigPO config = MybatisUtil.getOne(sysConfigMapper, SysConfigPO::getNid, nid);
        return config != null ? config.getContent() : null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0", cacheManager = "longCacheManager")
    public List<SysDictItem> getDictByNid(String nid) {
        return DataUtil.copy(MybatisUtil.getList(sysDictItemMapper, SysDictItemPO::getNid, nid), SysDictItem.class);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.AUTH_CONFIG, key = "#p0", unless = "#result == null", cacheManager = "longCacheManager")
    public AuthConfigVO getByAppId(String appId) {
        return authConfigMapper.getByAppId(appId);
    }

}
