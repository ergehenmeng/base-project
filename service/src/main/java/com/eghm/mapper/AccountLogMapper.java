package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.AccountQueryRequest;
import com.eghm.model.AccountLog;
import com.eghm.vo.business.account.AccountLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户资金变动明细表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface AccountLogMapper extends BaseMapper<AccountLog> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<AccountLogResponse> getByPage(Page<AccountLogResponse> page, @Param("param") AccountQueryRequest request);
}
