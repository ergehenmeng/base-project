package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.MerchantUser;
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
}
