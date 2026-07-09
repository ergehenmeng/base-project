package com.eghm.application.system.service.impl;

import com.eghm.application.system.port.out.SysDeptDataQueryGateway;
import com.eghm.application.system.port.in.SysDeptDataService;
import com.eghm.domain.system.model.SysDeptData;
import com.eghm.domain.system.repository.SysDeptDataRepository;
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
