package com.eghm.service.sys.impl;

import com.eghm.service.sys.SysDeptDataQueryGateway;
import com.eghm.service.sys.SysDeptDataService;
import com.eghm.sys.model.SysDeptData;
import com.eghm.sys.repository.SysDeptDataRepository;
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

    private final SysDeptDataRepository sysDeptDataRepository;

    private final SysDeptDataQueryGateway sysDeptDataQueryGateway;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDeptDataQueryGateway.getDeptList(userId);
    }

    @Override
    public void insert(SysDeptData dept) {
        sysDeptDataRepository.save(dept);
    }

    @Override
    public void deleteByUserId(Long userId) {
        sysDeptDataRepository.deleteByUserId(userId);
    }
}
