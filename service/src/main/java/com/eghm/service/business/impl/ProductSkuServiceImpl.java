package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dao.mapper.ProductSkuMapper;
import com.eghm.dao.model.ProductSku;
import com.eghm.model.dto.business.product.sku.ProductSkuRequest;
import com.eghm.service.business.ProductSkuService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productSkuService")
@AllArgsConstructor
public class ProductSkuServiceImpl implements ProductSkuService {

    private final ProductSkuMapper productSkuMapper;

    @Override
    public void create(Long productId, List<ProductSkuRequest> skuList) {
        for (ProductSkuRequest request : skuList) {
            ProductSku sku = DataUtil.copy(request, ProductSku.class, "id");
            sku.setProductId(productId);
            productSkuMapper.insert(sku);
        }
    }

    @Override
    public void update(Long productId, List<ProductSkuRequest> skuList) {
        // 删除
        LambdaUpdateWrapper<ProductSku> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductSku::getProductId, productId);
        productSkuMapper.delete(wrapper);
        // 更新
        List<ProductSkuRequest> updateList = skuList.stream().filter(sku -> sku.getId() != null).collect(Collectors.toList());
        for (ProductSkuRequest request : updateList) {
            ProductSku sku = DataUtil.copy(request, ProductSku.class);
            productSkuMapper.updateById(sku);
        }
        // 新增
        List<ProductSkuRequest> createList = skuList.stream().filter(sku -> sku.getId() == null).collect(Collectors.toList());
        this.create(productId, createList);
    }
}
