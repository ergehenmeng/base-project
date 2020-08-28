package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.system.SysOperatorDeptMapper;
import com.eghm.dao.model.system.SysOperatorDept;
import com.eghm.service.sys.SysOperatorDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@Service("sysOperatorDeptService")
public class SysOperatorDeptServiceImpl implements SysOperatorDeptService {

    private SysOperatorDeptMapper sysOperatorDeptMapper;

    @Autowired
    public void setSysOperatorDeptMapper(SysOperatorDeptMapper sysOperatorDeptMapper) {
        this.sysOperatorDeptMapper = sysOperatorDeptMapper;
    }


    @Override
    public List<String> getDeptList(Integer operatorId) {
        return sysOperatorDeptMapper.getDeptList(operatorId);
    }

    @Override
    public void insertSelective(SysOperatorDept dept) {
        sysOperatorDeptMapper.insertSelective(dept);
    }

    @Override
    public void deleteByOperatorId(Integer operatorId) {
        sysOperatorDeptMapper.deleteByOperatorId(operatorId);
    }
}
