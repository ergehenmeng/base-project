package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.SysDataDeptMapper;
import com.eghm.dao.model.SysDataDept;
import com.eghm.service.sys.SysDataDeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@Service("sysDataDeptService")
@AllArgsConstructor
public class SysDataDeptServiceImpl implements SysDataDeptService {

    private final SysDataDeptMapper sysDataDeptMapper;

    @Override
    public List<String> getDeptList(Long operatorId) {
        return sysDataDeptMapper.getDeptList(operatorId);
    }

    @Override
    public void insert(SysDataDept dept) {
        sysDataDeptMapper.insert(dept);
    }

    @Override
    public void deleteByOperatorId(Long operatorId) {
        sysDataDeptMapper.deleteByOperatorId(operatorId);
    }
}
