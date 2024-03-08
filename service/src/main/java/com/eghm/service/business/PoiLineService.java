package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.LinePointBindRequest;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.PoiLine;
import com.eghm.vo.poi.LinePointResponse;
import com.eghm.vo.poi.PoiLineResponse;
import com.eghm.vo.poi.PoiLineVO;

import java.util.List;

/**
 * <p>
 * poi线路表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
public interface PoiLineService {

    /**
     * 分页查询poi线路
     *
     * @param request 请求参数
     * @return poi线路列表
     */
    Page<PoiLineResponse> getByPage(PoiLineQueryRequest request);

    /**
     * 新增poi线路
     *
     * @param request 请求参数
     */
    void create(PoiLineAddRequest request);

    /**
     * 修改poi线路
     *
     * @param request 请求参数
     */
    void update(PoiLineEditRequest request);

    /**
     * 删除poi线路
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 线路关联点位信息
     *
     * @param request 线路及点位
     */
    void bindPoint(LinePointBindRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态 0:下架 1:上架
     */
    void updateState(Long id, Integer state);

    /**
     * 查询线路关联点位绑定情况
     *
     * @param id id
     * @return 点位及绑定情况
     */
    LinePointResponse getLinePoint(Long id);

    /**
     * 根据id查询poi线路
     *
     * @param id id
     * @return poi线路
     */
    PoiLine selectByIdRequired(Long id);

    /**
     * 查询所有线路
     *
     * @param areaCode 区域编号
     * @return 列表
     */
    List<PoiLineVO> getList(String areaCode);
}
