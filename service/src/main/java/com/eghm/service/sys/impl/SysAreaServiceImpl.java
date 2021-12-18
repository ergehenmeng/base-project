package com.eghm.service.sys.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.SysAreaMapper;
import com.eghm.dao.model.SysArea;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {

    private SysAreaMapper sysAreaMapper;

    @Autowired
    public void setSysAreaMapper(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void calcInitial() {
        List<SysArea> list = sysAreaMapper.getList();
        list.forEach(sysArea -> {
            String title = sysArea.getTitle();
            String initial = StringUtil.getInitial(title);
            sysArea.setMark(initial);
            sysAreaMapper.updateById(sysArea);
        });
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_ADDRESS, key = "#pid" ,cacheManager = "longCacheManager", sync = true)
    public List<SysAreaVO> getByPid(Long pid) {
        List<SysArea> addressList = sysAreaMapper.getByPid(pid);
        return DataUtil.convert(addressList, sysArea -> DataUtil.copy(sysArea, SysAreaVO.class));
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_ADDRESS, key = "#id", unless = "#result == null")
    public SysArea getById(Long id) {
        return sysAreaMapper.selectById(id);
    }
}
