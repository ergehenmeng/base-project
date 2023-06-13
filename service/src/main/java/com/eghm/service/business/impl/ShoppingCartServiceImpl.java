package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ref.PlatformState;
import com.eghm.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.ShoppingCartMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ShoppingCart;
import com.eghm.dto.business.shopping.AddCartDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.vo.business.shopping.ShoppingCartItemVO;
import com.eghm.vo.business.shopping.ShoppingCartVO;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.ShoppingCartService;
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

import static com.eghm.enums.ErrorCode.CART_ITEM_EMPTY;
import static com.eghm.enums.ErrorCode.ILLEGAL_OPERATION;
import static com.eghm.enums.ErrorCode.ITEM_DOWN;
import static com.eghm.enums.ErrorCode.ITEM_QUOTA;
import static com.eghm.enums.ErrorCode.ITEM_SKU_MATCH;
import static com.eghm.enums.ErrorCode.SHOPPING_CART_MAX;
import static com.eghm.enums.ErrorCode.SKU_STOCK;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Service("shoppingCartService")
@AllArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    private final SysConfigApi sysConfigApi;

    private final ItemService itemService;
    
    private final ItemSkuService itemSkuService;

    @Override
    public void add(AddCartDTO dto) {
        Long memberId = ApiHolder.getMemberId();
        LambdaQueryWrapper<ShoppingCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShoppingCart::getMemberId, memberId);
        wrapper.eq(ShoppingCart::getItemId, dto.getItemId());
        wrapper.eq(ShoppingCart::getSkuId, dto.getSkuId());
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(wrapper);

        int num = shoppingCart == null ? dto.getQuantity() : shoppingCart.getQuantity() + dto.getQuantity();

        ItemSku sku = this.checkAndGetSku(dto, num);

        if (shoppingCart == null) {
            this.checkCarMax(memberId);
            shoppingCart = DataUtil.copy(dto, ShoppingCart.class);
            shoppingCart.setSalePrice(sku.getSalePrice());
            Item item = itemService.selectByIdRequired(dto.getItemId());
            shoppingCart.setStoreId(item.getStoreId());
            shoppingCartMapper.insert(shoppingCart);
        } else {
            shoppingCart.setQuantity(num);
            shoppingCart.setSalePrice(shoppingCart.getSalePrice());
            shoppingCartMapper.updateById(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCartVO> getList(Long memberId) {
        List<ShoppingCartItemVO> voList = shoppingCartMapper.getList(memberId);
        // 根据根据店铺进行分组
        Map<Long, List<ShoppingCartItemVO>> listMap = voList.stream().collect(Collectors.groupingBy(ShoppingCartItemVO::getStoreId, LinkedHashMap::new, Collectors.toList()));
        List<ShoppingCartVO> vosList = new ArrayList<>();
        for (Map.Entry<Long, List<ShoppingCartItemVO>> entry : listMap.entrySet()) {
            ShoppingCartVO vo = new ShoppingCartVO();
            vo.setStoreId(entry.getKey());
            vo.setStoreTitle(entry.getValue().get(0).getStoreTitle());
            vo.setItemList(entry.getValue());
            vosList.add(vo);
        }
        return vosList;
    }

    @Override
    public void delete(Long id, Long memberId) {
        LambdaUpdateWrapper<ShoppingCart> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ShoppingCart::getId, id);
        wrapper.eq(ShoppingCart::getMemberId, memberId);
        shoppingCartMapper.delete(wrapper);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity, Long memberId) {
        ShoppingCart shoppingCart = shoppingCartMapper.selectById(id);
        if (shoppingCart == null) {
            log.error("未查询到购物车商品信息 [{}]", id);
            throw new BusinessException(CART_ITEM_EMPTY);
        }
        if (!memberId.equals(shoppingCart.getMemberId())) {
            log.error("非本人购物车信息, 禁止操作 [{}] [{}]", id, memberId);
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Item item = itemService.selectByIdRequired(shoppingCart.getItemId());
        if (item.getPlatformState() != PlatformState.SHELVE) {
            log.error("商品已下架, 无法更新购物车数量 [{}]", shoppingCart.getItemId());
            throw new BusinessException(ITEM_DOWN);
        }
        if (item.getQuota() < quantity) {
            log.error("超出商品最大限购数量, 无法更新购物车数量 [{}] [{}] [{}]", shoppingCart.getItemId(), item.getQuota(), quantity);
            throw new BusinessException(ITEM_QUOTA);
        }
    
        ItemSku itemSku = itemSkuService.selectByIdRequired(shoppingCart.getSkuId());

        if (itemSku.getStock() < quantity) {
            log.error("商品规格的库存不足, 无法更新购物车数量 [{}] [{}]", itemSku.getStock(), quantity);
            throw new BusinessException(SKU_STOCK);
        }

        shoppingCart.setQuantity(quantity);
        shoppingCartMapper.updateById(shoppingCart);
    }

    /**
     * 校验商品信息,并查询sku信息
     * @param dto 商品信息
     * @param quantity 添加总数量
     */
    private ItemSku checkAndGetSku(AddCartDTO dto, int quantity) {
        Item item = itemService.selectByIdRequired(dto.getItemId());
        if (item.getPlatformState() != PlatformState.SHELVE) {
            log.error("商品已下架,无法添加到购物车 [{}] [{}]", dto.getItemId(), item.getPlatformState());
            throw new BusinessException(ITEM_DOWN);
        }
        ItemSku itemSku = itemSkuService.selectByIdRequired(dto.getSkuId());

        if (!item.getId().equals(itemSku.getItemId())) {
            log.error("规格与商品不匹配 [{}] [{}]", dto.getSkuId(), dto.getItemId());
            throw new BusinessException(ITEM_SKU_MATCH);
        }
        if (itemSku.getStock() < quantity) {
            log.error("商品规格的库存不足 [{}] [{}]", itemSku.getStock(), quantity);
            throw new BusinessException(SKU_STOCK);
        }

        if (item.getQuota() < quantity) {
            log.error("超出商品的最大购买数量 [{}] [{}] [{}]", item.getId(), item.getQuota(), quantity);
            throw new BusinessException(ITEM_QUOTA);
        }
        return itemSku;
    }


    /**
     * 校验购物车最大值
     * @param memberId 用户
     */
    private void checkCarMax(Long memberId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShoppingCart::getMemberId, memberId);
        Long count = shoppingCartMapper.selectCount(wrapper);
        int max = sysConfigApi.getInt(ConfigConstant.SHOPPING_CAR_MAX);
        if (count >= max) {
            throw new BusinessException(SHOPPING_CART_MAX.getCode(), String.format(SHOPPING_CART_MAX.getMsg(), max));
        }
    }

}
