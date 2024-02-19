package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.ext.ProductScope;
import com.eghm.model.RedeemCodeScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兑换码配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
public interface RedeemCodeScopeMapper extends BaseMapper<RedeemCodeScope> {

    /**
     * 获取兑换码使用范围
     *
     * @param redeemCodeId 配置id
     * @return 使用范围
     */
    List<ProductScope> getScopeList(@Param("redeemCodeId") Long redeemCodeId);
}
