package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysMerchantRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMerchantRoleMapper extends BaseMapper<SysMerchantRole> {

    /**
     * 删除商户所有的角色
     * @param merchantId 商户ID
     * @return 影响条数
     */
    int deleteByMerchantId(@Param("merchantId") Long merchantId);

}