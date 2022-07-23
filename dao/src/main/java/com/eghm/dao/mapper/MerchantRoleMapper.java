package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.MerchantRole;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface MerchantRoleMapper extends BaseMapper<MerchantRole> {

    /**
     * 删除商户所有的角色
     * @param merchantId 商户ID
     * @return 影响条数
     */
    int deleteByMerchantId(@Param("merchantId") Long merchantId);

}