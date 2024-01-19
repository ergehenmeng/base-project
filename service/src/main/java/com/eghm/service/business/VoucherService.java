package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.restaurant.voucher.VoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.Voucher;
import com.eghm.vo.business.restaurant.VoucherDetailVO;
import com.eghm.vo.business.restaurant.VoucherResponse;
import com.eghm.vo.business.restaurant.VoucherVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
public interface VoucherService {

    /**
     * 分页查询餐饮券信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<Voucher> getByPage(VoucherQueryRequest request);

    /**
     * 分页查询餐饮券信息 导出使用
     *
     * @param request 查询条件
     * @return 列表
     */
    List<VoucherResponse> getList(VoucherQueryRequest request);

    /**
     * 创建餐饮券
     *
     * @param request 餐饮券信息
     */
    void create(VoucherAddRequest request);

    /**
     * 更新餐饮券
     *
     * @param request 餐饮券信息
     */
    void update(VoucherEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 主键查询餐饮券
     *
     * @param id id
     * @return 餐饮券
     */
    Voucher selectById(Long id);

    /**
     * 主键查询餐饮券,不存在抛异常
     *
     * @param id id
     * @return 餐饮券
     */
    Voucher selectByIdRequired(Long id);

    /**
     * 主键查询餐饮券,不是上架中的抛异常
     *
     * @param id id
     * @return 餐饮券
     */
    Voucher selectByIdShelve(Long id);

    /**
     * 更新库存信息
     *
     * @param id  id
     * @param num 负数-库存 正式+库存
     */
    void updateStock(Long id, Integer num);

    /**
     * 逻辑删除餐饮券
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 分页查询餐饮券
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<VoucherVO> getByPage(VoucherQueryDTO dto);

    /**
     * 查询餐饮券详细信息
     *
     * @param id id
     * @return 详细信息
     */
    VoucherDetailVO getDetail(Long id);
}
