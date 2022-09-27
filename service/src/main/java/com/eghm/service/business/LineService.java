package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Line;
import com.eghm.model.dto.business.line.LineAddRequest;
import com.eghm.model.dto.business.line.LineEditRequest;
import com.eghm.model.dto.business.line.LineQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
public interface LineService {

    /**
     * 分页查询线路新
     * @param request 查询条件
     * @return 线路列表
     */
    Page<Line> getByPage(LineQueryRequest request);

    /**
     * 新增线路信息
     * @param request 线路信息
     */
    void create(LineAddRequest request);

    /**
     * 更新线路信息
     * @param request 线路信息
     */
    void update(LineEditRequest request);

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);

    /**
     * 主键查询线路信息, 为空则抛异常
     * @param id id
     * @return 线路
     */
    Line selectByIdRequired(Long id);

    /**
     * 主键查询线路(没有上架则报错)
     * @param id id
     * @return 线路信息
     */
    Line selectByIdShelve(Long id);

    /**
     * 主键查询
     * @param id id
     * @return 线路
     */
    Line selectById(Long id);
}
