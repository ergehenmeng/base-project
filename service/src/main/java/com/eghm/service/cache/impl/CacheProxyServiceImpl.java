package com.eghm.service.cache.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CacheConstant;
import com.eghm.enums.Channel;
import com.eghm.enums.EmailType;
import com.eghm.mapper.*;
import com.eghm.model.*;
import com.eghm.service.business.ItemTagService;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.pay.enums.MerchantType;
import com.eghm.vo.business.item.ItemTagResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.constant.CommonConstant.LIMIT_ONE;

/**
 * 缓存代理层<br/>
 *
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Service("cacheProxyService")
@AllArgsConstructor
@Slf4j
public class CacheProxyServiceImpl implements CacheProxyService {

    private final SysAreaMapper sysAreaMapper;

    private final BannerMapper bannerMapper;

    private final EmailTemplateMapper emailTemplateMapper;

    private final NoticeTemplateMapper noticeTemplateMapper;

    private final PushTemplateMapper pushTemplateMapper;

    private final SysNoticeMapper sysNoticeMapper;

    private final SmsTemplateMapper smsTemplateMapper;

    private final SysConfigMapper sysConfigMapper;

    private final SysDictMapper sysDictMapper;

    private final AppletPayConfigMapper appletPayConfigMapper;

    private final ItemTagService itemTagService;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_ADDRESS, key = "#pid", cacheManager = "longCacheManager", sync = true)
    public List<SysArea> getAreaByPid(Long pid) {
        LambdaQueryWrapper<SysArea> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysArea::getPid, pid);
        return sysAreaMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_ADDRESS, key = "#id", unless = "#result == null")
    public SysArea getAreaById(Long id) {
        return sysAreaMapper.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.BANNER, cacheManager = "longCacheManager",  key = "#channel.name() + #classify", unless = "#result.size() == 0")
    public List<Banner> getBanner(Channel channel, Integer classify) {
        return bannerMapper.getBannerList(classify, channel.name());
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
    @Cacheable(cacheNames = CacheConstant.PUSH_TEMPLATE, key = "#p0", unless = "#result == null")
    public PushTemplate getPushTemplate(String nid) {
        LambdaQueryWrapper<PushTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PushTemplate::getNid, nid);
        wrapper.eq(PushTemplate::getState, 1);
        wrapper.last(LIMIT_ONE);
        return pushTemplateMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_NOTICE, cacheManager = "longCacheManager", unless = "#result.size() == 0")
    public List<SysNotice> getNoticeList(int limit) {
        return sysNoticeMapper.getTopList(limit);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SMS_TEMPLATE, key = "#p0", unless = "#result == null")
    public String getSmsTemplate(String nid) {
        LambdaQueryWrapper<SmsTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SmsTemplate::getNid, nid);
        wrapper.last(LIMIT_ONE);
        SmsTemplate template = smsTemplateMapper.selectOne(wrapper);
        return template != null ? template.getContent() : null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_CONFIG, key = "#p0", unless = "#result == null")
    public String getConfigByNid(String nid) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysConfig::getNid, nid);
        SysConfig config = sysConfigMapper.selectOne(wrapper);
        return config != null ? config.getContent() : null;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    public List<SysDict> getDictByNid(String nid) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getNid, nid);
        return sysDictMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.APPLET_PAY_CONFIG, cacheManager = "longCacheManager", key = "#type.code")
    public AppletPayConfig getPayByNid(MerchantType type) {
        LambdaQueryWrapper<AppletPayConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppletPayConfig::getNid, type.getCode());
        wrapper.last(LIMIT_ONE);
        return appletPayConfigMapper.selectOne(wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.ITEM_TAG, cacheManager = "longCacheManager")
    public List<ItemTagResponse> getList() {
        return itemTagService.getList();
    }
}
