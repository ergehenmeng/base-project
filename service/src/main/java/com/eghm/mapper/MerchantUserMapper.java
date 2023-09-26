package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.merchant.MerchantUserQueryRequest;
import com.eghm.model.MerchantUser;
import com.eghm.vo.business.merchant.MerchantUserResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
public interface MerchantUserMapper extends BaseMapper<MerchantUser> {

    /**
     * 查询商户对应的系统用户id
     * @param userId id
     * @return 系统用户id
     */
    Long getByUserId(@Param("userId") Long userId);

    /**
     * 分页查询商户用户列表
     * @param page 分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<MerchantUserResponse> getByPage(Page<MerchantUserResponse> page, @Param("param") MerchantUserQueryRequest request);
}
