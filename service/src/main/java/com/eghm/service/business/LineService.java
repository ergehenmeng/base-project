package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.line.LineAddRequest;
import com.eghm.dto.business.line.LineEditRequest;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ref.State;
import com.eghm.model.Line;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.line.LineDetailVO;
import com.eghm.vo.business.line.LineResponse;
import com.eghm.vo.business.line.LineVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
public interface LineService {

    /**
     * 分页查询线路新
     *
     * @param request 查询条件
     * @return 线路列表
     */
    Page<LineResponse> getByPage(LineQueryRequest request);

    /**
     * 分页查询线路新
     *
     * @param request 查询条件
     * @return 线路列表
     */
    List<LineResponse> getList(LineQueryRequest request);

    /**
     * 新增线路信息
     *
     * @param request 线路信息
     */
    void create(LineAddRequest request);

    /**
     * 更新线路信息
     *
     * @param request 线路信息
     */
    void update(LineEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 主键查询线路信息, 为空则抛异常
     *
     * @param id id
     * @return 线路
     */
    Line selectByIdRequired(Long id);

    /**
     * 分页查询线路列表
     *
     * @param dto 查询条件
     * @return 列表 不含总页数/总条数
     */
    List<LineVO> getByPage(LineQueryDTO dto);

    /**
     * 主键查询线路(没有上架则报错)
     *
     * @param id id
     * @return 线路信息
     */
    Line selectByIdShelve(Long id);

    /**
     * 主键查询
     *
     * @param id id
     * @return 线路
     */
    Line selectById(Long id);

    /**
     * 删除线路商品
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 线路详细信息
     *
     * @param id 线路id
     * @return 详细信息 包含行程信息
     */
    LineDetailVO detailById(Long id);

    /**
     * 更新线路和旅行社评分
     *
     * @param vo 线路id和旅行社id
     */
    void updateScore(CalcStatistics vo);

    /**
     * 分页查询商品列表
     *
     * @param request 查询条件
     * @return 基础信息
     */
    Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request);
}
