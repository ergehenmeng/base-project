package com.eghm.cache.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.cache.CacheProxyService;
import com.eghm.constants.CacheConstant;
import com.eghm.enums.Channel;
import com.eghm.enums.EmailType;
import com.eghm.mapper.*;
import com.eghm.model.*;
import com.eghm.vo.auth.AuthConfigVO;
import com.eghm.vo.banner.BannerVO;
import com.eghm.vo.sys.SysAreaVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.constants.CommonConstant.LIMIT_ONE;

/**
 * 缓存代理层<br/>
 * 防止循环依赖
 *
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Service("cacheProxyService")
@AllArgsConstructor
@Slf4j
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
    @Cacheable(cacheNames = CacheConstant.SYS_AREA, sync = true)
    public List<SysAreaVO> getAreaList() {
        return sysAreaMapper.getList(null);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_AREA_ID, key = "#id", unless = "#result == null")
    public SysArea getAreaById(Long id) {
        return sysAreaMapper.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BANNER, cacheManager = "longCacheManager", key = "#channel.name() + #bannerType", unless = "#result.size() == 0")
    public List<BannerVO> getBanner(Channel channel, Integer bannerType) {
        return bannerMapper.getBannerList(bannerType, channel.name());
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.EMAIL_TEMPLATE, key = "#code.name()", cacheManager = "longCacheManager", unless = "#result == null")
    public EmailTemplate getEmailTemplate(EmailType code) {
        LambdaQueryWrapper<EmailTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailTemplate::getNid, code.name());
        wrapper.last(LIMIT_ONE);
        return emailTemplateMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.IN_MAIL_TEMPLATE, key = "#p0", cacheManager = "longCacheManager", unless = "#result == null")
    public NoticeTemplate getNoticeTemplate(String code) {
        LambdaQueryWrapper<NoticeTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NoticeTemplate::getCode, code);
        wrapper.last(LIMIT_ONE);
        return noticeTemplateMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_NOTICE, cacheManager = "longCacheManager", unless = "#result.size() == 0")
    public List<SysNotice> getNoticeList(int limit) {
        return sysNoticeMapper.getTopList(limit);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_CONFIG, key = "#p0", unless = "#result == null")
    public String getConfigByNid(String nid) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysConfig::getNid, nid);
        wrapper.last(LIMIT_ONE);
        SysConfig config = sysConfigMapper.selectOne(wrapper);
        return config != null ? config.getContent() : null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    public List<SysDictItem> getDictByNid(String nid) {
        LambdaQueryWrapper<SysDictItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictItem::getNid, nid);
        return sysDictItemMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.AUTH_CONFIG, key = "#appKey", unless = "#result == null")
    public AuthConfigVO getByAppKey(String appKey) {
        return authConfigMapper.getByAppKey(appKey);
    }


}
