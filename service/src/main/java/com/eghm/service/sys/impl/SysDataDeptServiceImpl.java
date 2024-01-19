package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.SysDataDeptMapper;
import com.eghm.model.SysDataDept;
import com.eghm.service.sys.SysDataDeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/8/17
 */
@Service("sysDataDeptService")
@AllArgsConstructor
public class SysDataDeptServiceImpl implements SysDataDeptService {

    private final SysDataDeptMapper sysDataDeptMapper;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDataDeptMapper.getDeptList(userId);
    }

    @Override
    public void insert(SysDataDept dept) {
        sysDataDeptMapper.insert(dept);
    }

    @Override
    public void deleteByUserId(Long userId) {
        LambdaUpdateWrapper<SysDataDept> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysDataDept::getUserId, userId);
        sysDataDeptMapper.delete(wrapper);
    }
}
