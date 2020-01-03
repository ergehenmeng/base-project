package com.eghm.service.system;

import com.eghm.dao.model.system.BlackRoster;
import com.eghm.model.dto.system.roster.BlackRosterAddRequest;
import com.eghm.model.dto.system.roster.BlackRosterQueryRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:45
 */
public interface BlackRosterService {

    /**
     * 分页查询黑名单列表
     * @param request 查询条件
     * @return 列表
     */
    PageInfo<BlackRoster> getByPage(BlackRosterQueryRequest request);

    /**
     * 添加黑名单信息
     * @param request ip及时间
     */
    void addBlackRoster(BlackRosterAddRequest request);

    /**
     * 黑名单列表
     * @return 列表 ip与结束时间
     */
    List<BlackRoster> getAvailableList();
}
