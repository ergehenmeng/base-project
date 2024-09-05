package com.eghm.service.business;

import com.eghm.dto.ext.StoreScope;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.SysDictItem;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.statistics.ProductStatisticsVO;
import com.eghm.vo.sys.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
public interface CommonService {

    /**
     * 校验日期间隔是否超过最大值
     *
     * @param configNid 配置nid
     * @param maxValue  日期间隔值
     */
    void checkMaxDay(String configNid, long maxValue);

    /**
     * 根据订单编号查询处理类
     *
     * @param orderNo 订单编号 以ProductType中的prefix开头的订单
     * @return 处理类
     */
    <T> T getHandler(String orderNo, Class<T> clsHandler);

    /**
     * 订单支付成功异步回调处理逻辑
     *
     * @param context 异步通知上下文
     */
    void handlePayNotify(PayNotifyContext context);

    /**
     * 订单退款成功异步回调处理逻辑
     *
     * @param context 异步通知上下文
     */
    void handleRefundNotify(RefundNotifyContext context);

    /**
     * 根据商品类型查询accessHandler
     *
     * @param productType 商品类型
     * @param clsHandler  clsHandler
     * @param <T>         T
     * @return accessHandler
     */
    <T> T getHandler(ProductType productType, Class<T> clsHandler);

    /**
     * 根据给定的字典列表和标签id进行解析
     *
     * @param dictList 字典列表
     * @param tagIds   标签id 逗号分割
     * @return 标签名称
     */
    List<String> parseTags(List<SysDictItem> dictList, String tagIds);

    /**
     * 检查当前登陆用户是否为指定的商户id, 注意:如果登陆用户商户号(表示是管理员)为空则不校验
     *
     * @param merchantId 商户号
     */
    void checkIllegal(Long merchantId);

    /**
     * 检查当前登陆用户是否为指定的商户id, 注意:如果登陆用户商户号(表示是管理员)为空则不校验
     *
     * @param merchantId 商户号
     * @return true:非法 false:合法
     */
    boolean checkIsIllegal(Long merchantId);

    /**
     * 检查当前登陆用户是否为指定的商户id, 注意:如果登陆用户商户号(表示是管理员)为空则不校验
     *
     * @param merchantId      商户号
     * @param loginMerchantId 登录商户ID
     * @return true:非法 false:合法
     */
    boolean checkIsIllegal(Long merchantId, Long loginMerchantId);

    /**
     * 获取新增商品数量(按天) <br>
     * 注意: 不含场馆预约(场馆预约基本没意义)
     * @param request 查询条件
     * @return 列表
     */
    List<ProductStatisticsVO> dayAppend(ProductRequest request);

    /**
     * 查询店铺列表
     *
     * @param scopeIds 店铺ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(List<StoreScope> scopeIds);

    /**
     * 根据商品id获取店铺id
     *
     * @param productId 商品id
     * @param productType 商品类型
     * @return 店铺id
     */
    Long getStoreId(Long productId, ProductType productType);

    /**
     * 查询地址列表(树状结构)
     *
     * @return list
     */
    List<SysAreaVO> getTreeAreaList();

    /**
     * 查询地址列表(树状结构)
     *
     * @param gradeList 省份1级 市2级 县3级
     * @return list
     */
    List<SysAreaVO> getTreeAreaList(List<Integer> gradeList);
}
