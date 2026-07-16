package com.eghm.platform.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.platform.iam.mapper.SysDeptDataMapper;
import com.eghm.platform.iam.entity.SysDeptData;
import com.eghm.platform.iam.service.SysDeptDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/8/17
 */
@AllArgsConstructor
@Service("sysDeptDataService")
public class SysDeptDataServiceImpl implements SysDeptDataService {

    private final SysDeptDataMapper sysDeptDataMapper;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDeptDataMapper.getDeptList(userId);
    }

    @Override
    public void insert(SysDeptData dept) {
        sysDeptDataMapper.insert(dept);
    }

    @Override
    public void deleteByUserId(Long userId) {
        LambdaUpdateWrapper<SysDeptData> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysDeptData::getUserId, userId);
        sysDeptDataMapper.delete(wrapper);
    }
}
