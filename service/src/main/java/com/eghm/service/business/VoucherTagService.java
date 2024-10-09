package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.VoucherTagAddRequest;
import com.eghm.dto.business.VoucherTagEditRequest;
import com.eghm.dto.business.VoucherTagQueryRequest;
import com.eghm.vo.business.restaurant.VoucherTagResponse;


/**
* @author 二哥很猛
* @since 2024-10-09
*/
public interface VoucherTagService {

    /**
    * 分页查询记录
    * @param request 查询条件
    * @return 列表
    */
    Page<VoucherTagResponse> getByPage(VoucherTagQueryRequest request);

    /**
    * 新增
    *
    * @param request 信息
    */
    void create(VoucherTagAddRequest request);

    /**
    * 更新
    *
    * @param request 商品信息
    */
    void update(VoucherTagEditRequest request);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
    * 删除
    *
    * @param id id
    */
    void delete(Long id);
}