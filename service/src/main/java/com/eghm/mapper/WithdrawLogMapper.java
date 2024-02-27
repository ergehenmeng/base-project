package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.model.WithdrawLog;
import com.eghm.vo.business.withdraw.WithdrawLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户提现记录 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-16
 */
public interface WithdrawLogMapper extends BaseMapper<WithdrawLog> {

    /**
     * 分页查询
     *
     * @param page 分页
     * @param request 查询参数
     * @return 提现记录
     */
    Page<WithdrawLogResponse> getByPage(Page<WithdrawLogResponse> page, @Param("param") WithdrawQueryRequest request);
}
