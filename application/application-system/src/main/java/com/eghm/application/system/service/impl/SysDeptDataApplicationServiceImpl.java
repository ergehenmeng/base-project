package com.eghm.application.system.service.impl;

import com.eghm.application.system.query.SysDeptDataQueryService;
import com.eghm.application.system.service.SysDeptDataApplicationService;
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
public class SysDeptDataApplicationServiceImpl implements SysDeptDataApplicationService {

    private final SysDeptDataRepository sysDeptDataRepository;

    private final SysDeptDataQueryService sysDeptDataQueryService;

    @Override
    public List<String> getDeptList(Long userId) {
        return sysDeptDataQueryService.getDeptList(userId);
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
