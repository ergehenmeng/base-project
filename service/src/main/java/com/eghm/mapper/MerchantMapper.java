package com.eghm.mapper;

import com.eghm.model.Merchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商家信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
public interface MerchantMapper extends BaseMapper<Merchant> {

    /**
     * 查询商户对应的系统用户id
     * @param id id
     * @return 系统用户id
     */
    Long getOperatorId(@Param("id") Long id);
}
