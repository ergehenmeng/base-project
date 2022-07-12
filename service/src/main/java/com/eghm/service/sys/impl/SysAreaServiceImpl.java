package com.eghm.service.sys.impl;

import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.SysAreaMapper;
import com.eghm.dao.model.SysArea;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
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

    private final CacheProxyService cacheProxyService;

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
    public List<SysAreaVO> getByPid(Long pid) {
        List<SysArea> selectList = cacheProxyService.getAreaByPid(pid);
        return DataUtil.convert(selectList, sysArea -> DataUtil.copy(sysArea, SysAreaVO.class));

    }

    @Override
    public SysArea getById(Long id) {
        return cacheProxyService.getAreaById(id);
    }
}
