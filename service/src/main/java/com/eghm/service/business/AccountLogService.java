package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.AccountQueryRequest;
import com.eghm.vo.business.account.AccountLogResponse;

import java.util.List;

/**
 * <p>
 * 商户资金变动明细表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface AccountLogService {

    /**
     * 分页查询资金记录
     * @param request 查询条件
     * @return 列表
     */
    Page<AccountLogResponse> getByPage(AccountQueryRequest request);

    /**
     * 分页查询资金记录
     * @param request 查询条件
     * @return 列表
     */
    List<AccountLogResponse> getList(AccountQueryRequest request);
}
