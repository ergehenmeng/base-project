package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.ShoppingCarMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ShoppingCar;
import com.eghm.model.dto.business.shopping.AddCarDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.vo.shopping.ShoppingCarProductVO;
import com.eghm.model.vo.shopping.ShoppingCarVO;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.ShoppingCarService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eghm.common.enums.ErrorCode.CAR_PRODUCT_EMPTY;
import static com.eghm.common.enums.ErrorCode.ILLEGAL_OPERATION;
import static com.eghm.common.enums.ErrorCode.PRODUCT_DOWN;
import static com.eghm.common.enums.ErrorCode.PRODUCT_QUOTA;
import static com.eghm.common.enums.ErrorCode.PRODUCT_SKU_MATCH;
import static com.eghm.common.enums.ErrorCode.SHOPPING_CAR_MAX;
import static com.eghm.common.enums.ErrorCode.SKU_STOCK;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Service("shoppingCarService")
@AllArgsConstructor
@Slf4j
public class ShoppingCarServiceImpl implements ShoppingCarService {

    private final ShoppingCarMapper shoppingCarMapper;

    private final SysConfigApi sysConfigApi;

    private final ItemService itemService;
    
    private final ItemSkuService itemSkuService;

    @Override
    public void add(AddCarDTO dto) {
        Long userId = ApiHolder.getUserId();
        LambdaQueryWrapper<ShoppingCar> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShoppingCar::getUserId, userId);
        wrapper.eq(ShoppingCar::getProductId, dto.getProductId());
        wrapper.eq(ShoppingCar::getSkuId, dto.getSkuId());
        ShoppingCar shoppingCar = shoppingCarMapper.selectOne(wrapper);

        int num = shoppingCar == null ? dto.getQuantity() : shoppingCar.getQuantity() + dto.getQuantity();

        ItemSku sku = this.checkAndGetSku(dto, num);

        if (shoppingCar == null) {
            this.checkCarMax(userId);
            shoppingCar = DataUtil.copy(dto, ShoppingCar.class);
            shoppingCar.setSalePrice(sku.getSalePrice());
            Item item = itemService.selectByIdRequired(dto.getProductId());
            shoppingCar.setStoreId(item.getStoreId());
            shoppingCarMapper.insert(shoppingCar);
        } else {
            shoppingCar.setQuantity(num);
            shoppingCar.setSalePrice(shoppingCar.getSalePrice());
            shoppingCarMapper.updateById(shoppingCar);
        }
    }

    @Override
    public List<ShoppingCarVO> getList(Long userId) {
        List<ShoppingCarProductVO> voList = shoppingCarMapper.getList(userId);
        // 根据根据店铺进行分组
        Map<Long, List<ShoppingCarProductVO>> listMap = voList.stream().collect(Collectors.groupingBy(ShoppingCarProductVO::getStoreId, LinkedHashMap::new, Collectors.toList()));
        List<ShoppingCarVO> vosList = new ArrayList<>();
        for (Map.Entry<Long, List<ShoppingCarProductVO>> entry : listMap.entrySet()) {
            ShoppingCarVO vo = new ShoppingCarVO();
            vo.setStoreId(entry.getKey());
            vo.setStoreTitle(entry.getValue().get(0).getStoreTitle());
            vo.setProductList(entry.getValue());
            vosList.add(vo);
        }
        return vosList;
    }

    @Override
    public void delete(Long id, Long userId) {
        LambdaUpdateWrapper<ShoppingCar> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ShoppingCar::getId, id);
        wrapper.eq(ShoppingCar::getUserId, userId);
        shoppingCarMapper.delete(wrapper);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity, Long userId) {
        ShoppingCar shoppingCar = shoppingCarMapper.selectById(id);
        if (shoppingCar == null) {
            log.error("未查询到购物车商品信息 [{}]", id);
            throw new BusinessException(CAR_PRODUCT_EMPTY);
        }
        if (!userId.equals(shoppingCar.getUserId())) {
            log.error("非本人购物车信息, 禁止操作 [{}] [{}]", id, userId);
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Item item = itemService.selectByIdRequired(shoppingCar.getProductId());
        if (item.getPlatformState() != PlatformState.SHELVE) {
            log.error("商品已下架, 无法更新购物车数量 [{}]", shoppingCar.getProductId());
            throw new BusinessException(PRODUCT_DOWN);
        }
        if (item.getQuota() < quantity) {
            log.error("超出商品最大限购数量, 无法更新购物车数量 [{}] [{}] [{}]", shoppingCar.getProductId(), item.getQuota(), quantity);
            throw new BusinessException(PRODUCT_QUOTA);
        }
    
        ItemSku itemSku = itemSkuService.selectByIdRequired(shoppingCar.getSkuId());

        if (itemSku.getStock() < quantity) {
            log.error("商品规格的库存不足, 无法更新购物车数量 [{}] [{}]", itemSku.getStock(), quantity);
            throw new BusinessException(SKU_STOCK);
        }

        shoppingCar.setQuantity(quantity);
        shoppingCarMapper.updateById(shoppingCar);
    }

    /**
     * 校验商品信息,并查询sku信息
     * @param dto 商品信息
     * @param quantity 添加总数量
     */
    private ItemSku checkAndGetSku(AddCarDTO dto, int quantity) {
        Item item = itemService.selectByIdRequired(dto.getProductId());
        if (item.getPlatformState() != PlatformState.SHELVE) {
            log.error("商品已下架,无法添加到购物车 [{}] [{}]", dto.getProductId(), item.getPlatformState());
            throw new BusinessException(PRODUCT_DOWN);
        }
        ItemSku itemSku = itemSkuService.selectByIdRequired(dto.getSkuId());

        if (!item.getId().equals(itemSku.getItemId())) {
            log.error("规格与商品不匹配 [{}] [{}]", dto.getSkuId(), dto.getProductId());
            throw new BusinessException(PRODUCT_SKU_MATCH);
        }
        if (itemSku.getStock() < quantity) {
            log.error("商品规格的库存不足 [{}] [{}]", itemSku.getStock(), quantity);
            throw new BusinessException(SKU_STOCK);
        }

        if (item.getQuota() < quantity) {
            log.error("超出商品的最大购买数量 [{}] [{}] [{}]", item.getId(), item.getQuota(), quantity);
            throw new BusinessException(PRODUCT_QUOTA);
        }
        return itemSku;
    }


    /**
     * 校验购物车最大值
     * @param userId 用户
     */
    private void checkCarMax(Long userId) {
        LambdaQueryWrapper<ShoppingCar> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShoppingCar::getUserId, userId);
        Integer count = shoppingCarMapper.selectCount(wrapper);
        int max = sysConfigApi.getInt(ConfigConstant.SHOPPING_CAR_MAX);
        if (count >= max) {
            throw new BusinessException(SHOPPING_CAR_MAX.getCode(), String.format(SHOPPING_CAR_MAX.getMsg(), max));
        }
    }

}
