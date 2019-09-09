package com.fanyin.service.system.impl;

import com.fanyin.common.utils.StringUtil;
import com.fanyin.dao.mapper.system.SystemAddressMapper;
import com.fanyin.dao.model.system.SystemAddress;
import com.fanyin.service.system.SystemAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
@Service("systemAddressService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemAddressServiceImpl implements SystemAddressService {

    @Autowired
    private SystemAddressMapper systemAddressMapper;


    @Override
    public void calcInitial() {
        List<SystemAddress> list = systemAddressMapper.getList();
        list.forEach(systemAddress -> {
            String title = systemAddress.getTitle();
            String initial = StringUtil.getInitial(title);
            systemAddress.setMark(initial);
            systemAddressMapper.updateByPrimaryKeySelective(systemAddress);
        });
    }
}
