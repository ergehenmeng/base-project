package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.model.VerifyLog;
import com.eghm.vo.business.verify.VerifyLogResponse;

/**
 * @author 二哥很猛
 * @date 2022/8/6
 */
public interface VerifyLogService {

    /**
     * 分页查询核销记录
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<VerifyLogResponse> getByPage(VerifyLogQueryRequest request);

    /**
     * 统计某个订单被核销过的商品数量总数
     *
     * @param orderNo 订单编号
     * @return 数量
     */
    int getVerifiedNum(String orderNo);

    /**
     * 添加核销记录
     *
     * @param verifyLog 核销记录
     */
    void insert(VerifyLog verifyLog);
}
