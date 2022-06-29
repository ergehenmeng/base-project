package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Scenic;
import com.eghm.model.dto.scenic.ScenicAddRequest;
import com.eghm.model.dto.scenic.ScenicEditRequest;
import com.eghm.model.dto.scenic.ScenicQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/6/14 22:22
 */
public interface ScenicService {

    /**
     * 分页查询景区信息
     * @param request 查询条件
     * @return 景区信息
     */
    Page<Scenic> getByPage(ScenicQueryRequest request);

    /**
     * 新增景区
     * @param request 景区信息
     */
    void createScenic(ScenicAddRequest request);

    /**
     * 更新景区
     * @param request 景区信息
     */
    void updateScenic(ScenicEditRequest request);

    /**
     * 查询商户下的景区信息 <br>
     * 注意:当前一个商户只能有一个景区
     * @param merchantId 商户id
     * @return 景区
     */
    Scenic getByMerchantId(Long merchantId);

    /**
     * 主键查询景区信息
     * @param id id
     * @return 景区信息
     */
    Scenic selectById(Long id);
}
