package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.SysDataDeptMapper;
import com.eghm.dao.model.SysDataDept;
import com.eghm.service.sys.SysDataDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@Service("sysDataDeptService")
public class SysDataDeptServiceImpl implements SysDataDeptService {

    private SysDataDeptMapper sysDataDeptMapper;

    @Autowired
    public void setSysDataDeptMapper(SysDataDeptMapper sysDataDeptMapper) {
        this.sysDataDeptMapper = sysDataDeptMapper;
    }

    @Override
    public List<String> getDeptList(Integer operatorId) {
        return sysDataDeptMapper.getDeptList(operatorId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertSelective(SysDataDept dept) {
        sysDataDeptMapper.insertSelective(dept);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByOperatorId(Integer operatorId) {
        sysDataDeptMapper.deleteByOperatorId(operatorId);
    }
}