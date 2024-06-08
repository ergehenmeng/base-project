package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.homestay.HomestayAddRequest;
import com.eghm.dto.business.homestay.HomestayEditRequest;
import com.eghm.dto.business.homestay.HomestayQueryDTO;
import com.eghm.dto.business.homestay.HomestayQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ref.State;
import com.eghm.model.Homestay;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.homestay.BaseHomestayResponse;
import com.eghm.vo.business.homestay.HomestayDetailVO;
import com.eghm.vo.business.homestay.HomestayResponse;
import com.eghm.vo.business.homestay.HomestayVO;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/25
 */
public interface HomestayService {

    /**
     * 分页查询民宿列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<HomestayResponse> getByPage(HomestayQueryRequest request);

    /**
     * 获取民宿列表
     *
     * @return 列表
     */
    List<BaseHomestayResponse> getList();

    /**
     * 分页查询民宿列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<HomestayResponse> getList(HomestayQueryRequest request);

    /**
     * 新增民宿
     *
     * @param request 民宿信息
     */
    void create(HomestayAddRequest request);

    /**
     * 编辑保存民宿信息
     *
     * @param request 民宿信息
     */
    void update(HomestayEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 删除民宿
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 主键查询民宿信息, 如果为空,则抛异常
     *
     * @param id id
     * @return 民宿信息
     */
    Homestay selectByIdRequired(Long id);

    /**
     * 主键查询民宿(民宿没有上架则报错)
     *
     * @param id id
     * @return 民宿信息
     */
    Homestay selectByIdShelve(Long id);

    /**
     * 移动端分页查询民宿信息
     *
     * @param dto 查询跳进
     * @return 列表
     */
    List<HomestayVO> getByPage(HomestayQueryDTO dto);

    /**
     * 民宿详细信息查询 包含推荐房型
     *
     * @param homestayId 民宿id
     * @return 民宿信息
     */
    HomestayDetailVO detailById(Long homestayId);

    /**
     * 更新民宿和房型分数
     *
     * @param vo 商品信息
     */
    void updateScore(CalcStatistics vo);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request);

    /**
     * 注销商户的民宿
     *
     * @param merchantId 商户ID
     */
    void logout(Long merchantId);
}
