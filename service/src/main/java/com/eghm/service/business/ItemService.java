package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.item.*;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ref.State;
import com.eghm.model.Item;
import com.eghm.vo.business.item.ItemResponse;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.ItemDetailResponse;
import com.eghm.vo.business.item.ItemDetailVO;
import com.eghm.vo.business.item.express.TotalExpressVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
public interface ItemService {
    
    /**
     * 分页查询商品信息
     * @param request 分页信息及查询条件
     * @return 商品列表
     */
    Page<ItemResponse> getByPage(ItemQueryRequest request);

    /**
     * 分页查询商品信息 (导出)
     * @param request 分页信息及查询条件
     * @return 商品列表
     */
    List<ItemResponse> getList(ItemQueryRequest request);

    /**
     * 创建零售商品
     * @param request 商品信息
     */
    void create(ItemAddRequest request);
    
    /**
     * 更新零售商品
     * @param request 商品信息
     */
    void update(ItemEditRequest request);
    
    /**
     * 查询零售商品详情
     * @param itemId 商品id
     * @return 详情 包含sku spec等信息
     */
    ItemDetailResponse getDetailById(Long itemId);
    
    /**
     * 主键查询零售商品
     * @param itemId id
     * @return 零售
     */
    Item selectById(Long itemId);
    
    /**
     * 主键查询零售商品, 为空报错
     * @param itemId id
     * @return 零售
     */
    Item selectByIdRequired(Long itemId);
    
    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 设置商品为推荐商品
     * @param id 商品id
     */
    void setRecommend(Long id);
    
    /**
     * 商品排序
     * @param id 商品id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);
    
    /**
     * 根据id查询商品
     * @param ids 商品id
     * @return 商品信息 key为商品id
     */
    Map<Long, Item> getByIdShelveMap(Set<Long> ids);
    
    /**
     * 更新销售量
     * @param itemNumMap 商品及销售数量
     */
    void updateSaleNum(Map<Long, Integer> itemNumMap);
    
    /**
     * 更新销售量
     * @param id  商品id
     * @param num 销量量-减销量 +增销量
     */
    void updateSaleNum(Long id, Integer num);
    
    /**
     * 根据订单编号更新订单号下的所有商品销售量
     * @param orderNoList 订单号
     */
    void updateSaleNum(List<String> orderNoList);
    
    /**
     * 查询<b>店铺首页</b>推荐的商品, 注意:
     * 1. 如果推荐商品本身没有规格,则不会优先展示
     * 2. 如果没有指定排序则以最新且上架的商品进行展示
     * @param shopId 店铺id
     * @return 推荐商品
     */
    List<ItemVO> getPriorityItem(Long shopId);
    
    /**
     * 查询<b>首页</b>推荐的商品, 注意:
     * 1. 如果推荐商品本身没有规格,则不会优先展示
     * 2. 如果没有推荐默认以最新且上架的商品进行展示
     * @return 推荐商品
     */
    List<ItemVO> getRecommend();
    
    /**
     * 商品列表查询 移动端
     * @param dto 查询条件
     * @return 列表
     */
    List<ItemVO> getByPage(ItemQueryDTO dto);
    
    /**
     * 优惠券所能使用的商品范围, 分页查询
     * @param dto 查询条件
     * @return 列表
     */
    List<ItemVO> getCouponScopeByPage(ItemCouponQueryDTO dto);

    /**
     * 计算商品快递费
     * 1. 由于存在购物车下单, 因此需要按店铺计算每个店铺的快递费
     * 2. 由于同一个店铺可能会多商品同时下单, 因此店铺费用=每个商品的费用相加
     * 3. 为了防止恶意调用(传入A店铺的storeId, 购买的B调用的itemId), 因此商品查询时会校验参数是否合法
     * 4. 为了防止恶意调用(传入A商品的itemId, 购买的B商品的skuId), 因此商品sku查询时也会校验
     * 5. 同一店铺按商品计费方式分组计算计件和计重的价格
     * @param dtoList 商品列表, 按店铺进行分组
     * @return 快递费, 按门店进行计算
     */
    TotalExpressVO calcExpressFee(List<ExpressFeeCalcDTO> dtoList);

    /**
     * 计算单店铺快递费用
     * @param dto 一个店铺内下单的商品信息
     * @return 费用 分:
     */
    Integer calcStoreExpressFee(ExpressFeeCalcDTO dto);

    /**
     * 更新商品和门店评分
     * @param vo 商品和门店信息
     */
    void updateScore(CalcStatistics vo);

    /**
     * 零售商品信息查询
     * @param id 商品信息
     * @return 商品信息
     */
    ItemDetailVO detailById(Long id);

    /**
     * 删除零售商品
     * @param id id
     */
    void deleteById(Long id);
}
