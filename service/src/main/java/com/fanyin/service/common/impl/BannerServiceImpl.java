package com.fanyin.service.common.impl;

import com.fanyin.common.enums.Channel;
import com.fanyin.common.enums.SourceClassify;
import com.fanyin.dao.mapper.common.BannerMapper;
import com.fanyin.dao.model.common.Banner;
import com.fanyin.service.common.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/10/17 9:50
 */
@Service("bannerService")
@Transactional(rollbackFor = RuntimeException.class)
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;


    @Override
    public List<Banner> getBanner(Channel source, Byte type) {
        byte clientType = SourceClassify.getType(source);
        return bannerMapper.getBannerList(type,clientType);
    }
}
