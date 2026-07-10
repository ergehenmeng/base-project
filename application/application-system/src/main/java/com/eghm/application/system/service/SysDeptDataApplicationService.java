package com.eghm.application.system.service;

import com.eghm.application.system.query.SysDeptDataQueryService;
import com.eghm.domain.system.model.SysDeptData;
import com.eghm.domain.system.repository.SysDeptDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/8/17
 */
@Service
@AllArgsConstructor
public class SysDeptDataApplicationService {

    private final SysDeptDataRepository sysDeptDataRepository;

    private final SysDeptDataQueryService sysDeptDataQueryService;

    /**
     * 获取用户所拥有的所有部门(数据权限)
     *
     * @param userId 用户id
     * @return 部门id
     */
    public List<String> getDeptList(Long userId) {
        return sysDeptDataQueryService.getDeptList(userId);
    }

    /**
     * 插入用户与部门数据权限关联信息
     *
     * @param dept userId + deptId
     */
    public void insert(SysDeptData dept) {
        sysDeptDataRepository.save(dept);
    }

    /**
     * 删除用户对应的部门的数据权限
     *
     * @param userId 用户id
     */
    public void deleteByUserId(Long userId) {
        sysDeptDataRepository.deleteByUserId(userId);
    }
}
