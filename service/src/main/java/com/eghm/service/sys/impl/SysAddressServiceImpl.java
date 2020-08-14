package com.eghm.service.sys.impl;

import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.sys.SysAddressMapper;
import com.eghm.dao.model.sys.SysAddress;
import com.eghm.service.sys.SysAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
@Service("sysAddressService")
@Transactional(rollbackFor = RuntimeException.class)
public class SysAddressServiceImpl implements SysAddressService {

    private SysAddressMapper sysAddressMapper;

    @Autowired
    public void setSysAddressMapper(SysAddressMapper sysAddressMapper) {
        this.sysAddressMapper = sysAddressMapper;
    }

    @Override
    public void calcInitial() {
        List<SysAddress> list = sysAddressMapper.getList();
        list.forEach(sysAddress -> {
            String title = sysAddress.getTitle();
            String initial = StringUtil.getInitial(title);
            sysAddress.setMark(initial);
            sysAddressMapper.updateByPrimaryKeySelective(sysAddress);
        });
    }
}
