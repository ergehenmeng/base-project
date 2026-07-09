package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.family.FamilyAddRequest;
import com.eghm.application.shared.dto.sys.family.FamilyEditRequest;
import com.eghm.application.shared.vo.sys.family.FamilyResponse;

/**
 * @author 二哥很猛
 * @since 2025/12/16
 */
public interface FamilyApplicationService {

    /**
     * 获取用户列表
     *
     * @return tree结构
     */
    FamilyResponse getList();

    /**
     * 创建用户信息
     *
     * @param request 用户信息
     * @return id
     */
    String create(FamilyAddRequest request);

    /**
     * 更新用户信息
     *
     * @param request 用户信息
     */
    void update(FamilyEditRequest request);

    /**
     * 删除用户信息
     *
     * @param id 用户id
     */
    void delete(String id);
}
