package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ExpressTemplate;
import com.eghm.vo.business.item.express.ExpressTemplateResponse;
import com.eghm.vo.business.item.express.ExpressTemplateVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 快递模板表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ExpressTemplateMapper extends BaseMapper<ExpressTemplate> {

    /**
     * 查询同一店铺下的商品对应的快递模板
     *
     * @param itemIds 商品Id
     * @param storeId 商户
     * @return 快递模板
     */
    List<ExpressTemplateVO> getExpressList(@Param("itemIds") List<Long> itemIds, @Param("storeId") Long storeId);

    /**
     * 查询商户下的物流模板
     *
     * @param merchantId 商户ID
     * @return 模板
     */
    List<ExpressTemplateResponse> getList(@Param("merchantId") Long merchantId);
}
