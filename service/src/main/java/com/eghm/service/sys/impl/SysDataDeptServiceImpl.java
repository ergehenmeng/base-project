package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.SysDataDeptMapper;
import com.eghm.dao.model.SysDataDept;
import com.eghm.service.common.KeyGenerator;
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

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setSysDataDeptMapper(SysDataDeptMapper sysDataDeptMapper) {
        this.sysDataDeptMapper = sysDataDeptMapper;
    }

    @Override
    public List<String> getDeptList(Long operatorId) {
        return sysDataDeptMapper.getDeptList(operatorId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(SysDataDept dept) {
        dept.setId(keyGenerator.generateKey());
        sysDataDeptMapper.insert(dept);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByOperatorId(Long operatorId) {
        sysDataDeptMapper.deleteByOperatorId(operatorId);
    }
}
