package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.CouponConfigMapper;
import com.eghm.mapper.ProductMapper;
import com.eghm.model.CouponConfig;
import com.eghm.model.Product;
import com.eghm.model.dto.business.product.*;
import com.eghm.model.dto.business.product.sku.ProductSkuRequest;
import com.eghm.model.vo.business.product.ProductListResponse;
import com.eghm.model.vo.business.product.ProductListVO;
import com.eghm.service.business.ProductService;
import com.eghm.service.business.ProductSkuService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.common.enums.ErrorCode.PRODUCT_DOWN;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productService")
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductSkuService productSkuService;

    private final SysConfigApi sysConfigApi;

    private final CouponConfigMapper couponConfigMapper;

    @Override
    public Page<ProductListResponse> getByPage(ItemQueryRequest request) {
        // TODO 过滤当前登录人的storeId
        return productMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(ProductAddRequest request) {
        this.titleRedo(request.getTitle(), null, request.getStoreId());
        Product product = DataUtil.copy(request, Product.class);
        this.setMinMaxPrice(product, request.getSkuList());
        // 总销量需要添加虚拟销量
        product.setTotalNum(request.getVirtualNum());
        productMapper.insert(product);
        productSkuService.create(product.getId(), request.getSkuList());
    }

    @Override
    public void update(ProductEditRequest request) {
        this.titleRedo(request.getTitle(), request.getId(), request.getStoreId());
        Product product = DataUtil.copy(request, Product.class);
        this.setMinMaxPrice(product, request.getSkuList());

        Product origin = productMapper.selectById(request.getId());
        // 总销量=源真实销售量+虚拟销量
        product.setTotalNum(origin.getSaleNum() + request.getVirtualNum());
        productMapper.updateById(product);
        productSkuService.update(product.getId(), request.getSkuList());
    }

    @Override
    public Product selectById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public Product selectByIdRequired(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            log.error("该普通商品已删除 [{}]", id);
            throw new BusinessException(PRODUCT_DOWN);
        }
        return product;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getState, state);
        productMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getPlatformState, state);
        productMapper.update(null, wrapper);
    }

    @Override
    public void setRecommend(Long id) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getRecommend, true);
        productMapper.update(null, wrapper);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getSortBy, sortBy);
        productMapper.update(null, wrapper);
    }

    @Override
    public Map<Long, Product> getByIds(Set<Long> ids) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(Product::getId, ids);
        wrapper.eq(Product::getPlatformState, PlatformState.SHELVE);
        List<Product> productList = productMapper.selectList(wrapper);
        if (productList.size() != ids.size()) {
            log.info("存在已下架的商品 {}", ids);
            throw new BusinessException(PRODUCT_DOWN);
        }
        return productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    @Override
    public void updateSaleNum(Map<Long, Integer> productNumMap) {
        for (Map.Entry<Long, Integer> entry : productNumMap.entrySet()) {
            productMapper.updateSaleNum(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void updateSaleNum(Long id, Integer num) {
        productMapper.updateSaleNum(id, num);
    }

    @Override
    public void updateSaleNum(List<String> orderNoList) {
        orderNoList.forEach(productMapper::updateSaleNumByOrderNo);
    }

    @Override
    public List<ProductListVO> getPriorityProduct(Long shopId) {
        int max = sysConfigApi.getInt(ConfigConstant.STORE_PRODUCT_MAX_RECOMMEND, 10);
        return productMapper.getPriorityProduct(shopId, max);
    }

    @Override
    public List<ProductListVO> getRecommend() {
        int max = sysConfigApi.getInt(ConfigConstant.PRODUCT_MAX_RECOMMEND, 10);
        return productMapper.getRecommendProduct(max);
    }

    @Override
    public List<ProductListVO> getByPage(ItemQueryDTO dto) {
        Page<ProductListVO> voPage = productMapper.getByPage(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public List<ProductListVO> getCouponScopeByPage(ItemCouponQueryDTO dto) {
        CouponConfig coupon = couponConfigMapper.selectById(dto.getCouponId());
        if (coupon == null) {
            log.error("优惠券不存在 [{}]", dto.getCouponId());
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        // 增加过滤条件,提高查询效率
        dto.setStoreId(coupon.getStoreId());
        dto.setUseScope(coupon.getUseScope());
        return productMapper.getCouponScopeByPage(dto.createPage(false), dto).getRecords();
    }

    /**
     * 同一家店铺 商品名称重复校验
     * @param productName 商品名称
     * @param id 商品id
     * @param storeId 店铺id
     */
    private void titleRedo(String productName, Long id, Long storeId) {
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Product::getTitle, productName);
        wrapper.ne(id != null, Product::getId, id);
        wrapper.eq(Product::getStoreId, storeId);
        Integer count = productMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("零售商品名称重复 [{}] [{}] [{}]", productName, id, storeId);
            throw new BusinessException(ErrorCode.PRODUCT_TITLE_REDO);
        }
    }

    /**
     * 设置商品的最大值和最小值
     * @param product 商品信息
     * @param skuList 商品sku的信息
     */
    private void setMinMaxPrice(Product product, List<ProductSkuRequest> skuList) {
        if (CollUtil.isNotEmpty(skuList)) {
            OptionalInt max = skuList.stream().mapToInt(ProductSkuRequest::getSalePrice).max();
            if (max.isPresent()) {
                product.setMaxPrice(max.getAsInt());
            }
            OptionalInt min = skuList.stream().mapToInt(ProductSkuRequest::getSalePrice).min();
            if (min.isPresent()) {
                product.setMinPrice(min.getAsInt());
            }
        }
    }
}
