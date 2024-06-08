package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.model.Merchant;
import com.eghm.vo.business.merchant.BaseMerchantResponse;
import com.eghm.vo.business.merchant.MerchantResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 分页查询商户信息
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return list
     */
    Page<MerchantResponse> listPage(Page<MerchantResponse> page, @Param("param") MerchantQueryRequest request);

    /**
     * 查询商户对应的系统用户id
     *
     * @param userId id
     * @return 系统用户id
     */
    Long getByUserId(@Param("userId") Long userId);
    
    /**
     * 查询商户基础信息
     *
     * @return 商户信息
     */
    List<BaseMerchantResponse> getList();
}
