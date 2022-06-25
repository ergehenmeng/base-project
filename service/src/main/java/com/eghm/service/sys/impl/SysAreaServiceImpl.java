package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.SysAreaMapper;
import com.eghm.dao.model.SysArea;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
@Service("sysAreaService")
@AllArgsConstructor
public class SysAreaServiceImpl implements SysAreaService {

    private final SysAreaMapper sysAreaMapper;

    @Override
    public void calcInitial() {
        List<SysArea> list = sysAreaMapper.selectList(null);
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
        LambdaQueryWrapper<SysArea> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysArea::getPid, pid);
        List<SysArea> selectList = sysAreaMapper.selectList(wrapper);
        return DataUtil.convert(selectList, sysArea -> DataUtil.copy(sysArea, SysAreaVO.class));
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_ADDRESS, key = "#id", unless = "#result == null")
    public SysArea getById(Long id) {
        return sysAreaMapper.selectById(id);
    }
}
