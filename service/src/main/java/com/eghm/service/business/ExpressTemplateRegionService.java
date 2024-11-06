package com.eghm.service.business;

import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ExpressTemplateRegionRequest;
import com.eghm.model.ExpressTemplateRegion;

import java.util.List;

/**
 * <p>
 * 快递模板区域 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ExpressTemplateRegionService {

    /**
     * 新增或更新快递区域运费配置信息
     *
     * @param expressId  模板id
     * @param regionList 价格信息
     */
    void createOrUpdate(Long expressId, List<ExpressTemplateRegionRequest> regionList);

    /**
     * 计算同一店铺的某个订单快递费
     * 1. 如果expressId为空表示免快递费
     * 2. 查询物流模板下所有的区域价格配置信息,并按expressId分组(一次性查询完,减少查询次数)
     * 3. 将所有商品物流信息按计费方式进行分组(分组计算,减少代码复杂度)
     * 4. 计重时: 根据skuId查询重量, 然后根据重量计算费用
     * 5. 计件时: 根据itemId计算总数量, 然后根据数量计算费用
     *
     * @param dto 店铺商品信息
     * @return 费用 单位:分
     */
    Integer calcFee(ExpressFeeCalcDTO dto);
}
