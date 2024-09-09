package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.freeze.AccountFreezeQueryRequest;
import com.eghm.model.AccountFreezeLog;
import com.eghm.vo.business.freeze.AccountFreezeLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户资金冻结记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-28
 */
public interface AccountFreezeLogMapper extends BaseMapper<AccountFreezeLog> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<AccountFreezeLogResponse> getByPage(Page<AccountFreezeLogResponse> page, @Param("param") AccountFreezeQueryRequest request);
}
