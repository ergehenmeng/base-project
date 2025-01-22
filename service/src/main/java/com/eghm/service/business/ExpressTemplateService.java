package com.eghm.service.business;

import com.eghm.dto.business.item.express.ExpressTemplateAddRequest;
import com.eghm.dto.business.item.express.ExpressTemplateEditRequest;
import com.eghm.model.ExpressTemplate;
import com.eghm.vo.business.item.express.ExpressSelectResponse;
import com.eghm.vo.business.item.express.ExpressTemplateResponse;
import com.eghm.vo.business.item.express.ExpressTemplateVO;

import java.util.List;

/**
 * <p>
 * 快递模板表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ExpressTemplateService {

    /**
     * 查询商户下的物流模板
     *
     * @param merchantId 商户ID
     * @return 模板
     */
    List<ExpressTemplateResponse> getList(Long merchantId);

    /**
     * 查询商户下的物流模板(下拉框使用)
     *
     * @param merchantId 商户ID
     * @return 列表
     */
    List<ExpressSelectResponse> selectList(Long merchantId);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 模板信息
     */
    ExpressTemplateResponse selectById(Long id);

    /**
     * 新增快递模板
     *
     * @param request 模板信息
     */
    void create(ExpressTemplateAddRequest request);

    /**
     * 更新快递模板
     *
     * @param request 模板信息
     */
    void update(ExpressTemplateEditRequest request);

    /**
     * 查询商品对应的快递模板
     *
     * @param itemIds 商品Id
     * @param storeId 店铺Id
     * @return 快递模板
     */
    List<ExpressTemplateVO> getExpressList(List<Long> itemIds, Long storeId);

    /**
     * 逻辑删除快递模板
     *
     * @param id 快递id
     */
    void deleteById(Long id);

    /**
     * 主键查询快递模板
     *
     * @param id id
     * @return 模板消息
     */
    ExpressTemplate selectByIdRequired(Long id);
}
