package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.scenic.*;
import com.eghm.enums.ref.State;
import com.eghm.model.Scenic;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.scenic.ScenicDetailVO;
import com.eghm.vo.business.scenic.ScenicVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/14
 */
public interface ScenicService {

    /**
     * 分页查询景区信息
     *
     * @param request 查询条件
     * @return 景区信息
     */
    Page<Scenic> getByPage(ScenicQueryRequest request);

    /**
     * 新增景区
     *
     * @param request 景区信息
     */
    void createScenic(ScenicAddRequest request);

    /**
     * 更新景区
     *
     * @param request 景区信息
     */
    void updateScenic(ScenicEditRequest request);

    /**
     * 查询商户下的景区信息 <br>
     * 注意:当前一个商户只能有一个景区
     *
     * @param merchantId 商户id
     * @return 景区
     */
    Scenic getByMerchantId(Long merchantId);

    /**
     * 主键查询景区信息
     *
     * @param id id
     * @return 景区信息
     */
    Scenic selectById(Long id);

    /**
     * 查询景区信息, 如果景区删除或下架则报错
     *
     * @param id id
     * @return 景区信息
     */
    Scenic selectByIdShelve(Long id);

    /**
     * 查询景区信息, 如果景区删除则报错
     *
     * @param id id
     * @return 景区信息
     */
    Scenic selectByIdRequired(Long id);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 景区列表查询
     *
     * @param dto 查询条件
     * @return 列表 不含分页信息
     */
    List<ScenicVO> getByPage(ScenicQueryDTO dto);

    /**
     * 查询景区详细信息 含门票
     *
     * @param dto id及用户经纬度
     * @return 景区详细
     */
    ScenicDetailVO detailById(ScenicDetailDTO dto);

    /**
     * 删除景区,逻辑删除
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新景区价格(最低价,最高价)
     *
     * @param id 景区id
     */
    void updatePrice(Long id);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request);
}
