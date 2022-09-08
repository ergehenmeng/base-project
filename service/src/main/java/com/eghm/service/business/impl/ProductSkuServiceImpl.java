package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.ProductSkuMapper;
import com.eghm.dao.model.ProductSku;
import com.eghm.model.dto.business.product.sku.ProductSkuRequest;
import com.eghm.service.business.ProductSkuService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.common.enums.ErrorCode.SKU_DOWN;
import static com.eghm.common.enums.ErrorCode.SKU_STOCK;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productSkuService")
@AllArgsConstructor
@Slf4j
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

    @Override
    public ProductSku selectById(Long skuId) {
        return productSkuMapper.selectById(skuId);
    }

    @Override
    public ProductSku selectByIdRequired(Long skuId) {
        ProductSku sku = productSkuMapper.selectById(skuId);
        if (sku == null) {
            log.error("商品规格已删除 [{}]", skuId);
            throw new BusinessException(ErrorCode.SKU_DOWN);
        }
        return sku;
    }

    @Override
    public Map<Long, ProductSku> getByIds(Set<Long> ids) {
        LambdaUpdateWrapper<ProductSku> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(ProductSku::getId, ids);
        List<ProductSku> skuList = productSkuMapper.selectList(wrapper);
        if (skuList.size() != ids.size()) {
            log.info("存在已下架的商品规格 {}", ids);
            throw new BusinessException(SKU_DOWN);
        }
        return skuList.stream().collect(Collectors.toMap(ProductSku::getId, Function.identity()));
    }

    @Override
    public void updateStock(Long skuId, Integer num) {
        int stock = productSkuMapper.updateStock(skuId, num);
        if (stock != 1) {
            log.error("商品更新库存失败 [{}] [{}] [{}]", skuId, num, stock);
            throw new BusinessException(SKU_STOCK);
        }
    }

    @Override
    public void updateStock(Map<Long, Integer> map) {

    }
}
