package com.eghm.business.operation.delivery.service;

import com.eghm.business.operation.delivery.entity.NoticeTemplate;
import com.eghm.business.operation.delivery.entity.SysNotice;
import com.eghm.business.operation.delivery.mapper.BannerMapper;
import com.eghm.business.operation.delivery.mapper.NoticeTemplateMapper;
import com.eghm.business.operation.delivery.mapper.SysNoticeMapper;
import com.eghm.business.operation.delivery.vo.BannerVO;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.enums.Channel;
import com.eghm.foundation.web.utility.MybatisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("deliveryCacheService")
@RequiredArgsConstructor
public class DeliveryCacheService {

    private final BannerMapper bannerMapper;
    private final SysNoticeMapper sysNoticeMapper;
    private final NoticeTemplateMapper noticeTemplateMapper;

    @Cacheable(cacheNames = CacheConstant.BANNER, key = "#channel.name() + #p1", unless = "#result.size() == 0", cacheManager = "longCacheManager")
    public List<BannerVO> getBanner(Channel channel, Integer bannerType) {
        return bannerMapper.getBannerList(channel.name(), bannerType);
    }

    @Cacheable(cacheNames = CacheConstant.IN_MAIL_TEMPLATE, key = "#p0", unless = "#result == null", cacheManager = "longCacheManager")
    public NoticeTemplate getNoticeTemplate(String code) {
        return MybatisUtil.getOne(noticeTemplateMapper, NoticeTemplate::getCode, code);
    }

    @Cacheable(cacheNames = CacheConstant.SYS_NOTICE, unless = "#result.size() == 0", cacheManager = "longCacheManager")
    public List<SysNotice> getNoticeList(int limit) {
        return sysNoticeMapper.getTopList(limit);
    }
}
