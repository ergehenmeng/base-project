package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.model.Scenic;
import com.eghm.model.dto.business.scenic.ScenicAddRequest;
import com.eghm.model.dto.business.scenic.ScenicEditRequest;
import com.eghm.model.dto.business.scenic.ScenicQueryDTO;
import com.eghm.model.dto.business.scenic.ScenicQueryRequest;
import com.eghm.model.vo.scenic.ScenicListVO;
import com.eghm.model.vo.scenic.ScenicVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/6/14
 */
public interface ScenicService {

    /**
     * 分页查询景区信息
     * @param request 查询条件
     * @return 景区信息
     */
    Page<Scenic> getByPage(ScenicQueryRequest request);

    /**
     * 新增景区
     * @param request 景区信息
     */
    void createScenic(ScenicAddRequest request);

    /**
     * 更新景区
     * @param request 景区信息
     */
    void updateScenic(ScenicEditRequest request);

    /**
     * 查询商户下的景区信息 <br>
     * 注意:当前一个商户只能有一个景区
     * @param merchantId 商户id
     * @return 景区
     */
    Scenic getByMerchantId(Long merchantId);

    /**
     * 主键查询景区信息
     * @param id id
     * @return 景区信息
     */
    Scenic selectById(Long id);

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);

    /**
     * 景区列表查询
     * @param dto 查询条件
     * @return 列表 不含分页信息
     */
    List<ScenicListVO> getByPage(ScenicQueryDTO dto);

    /**
     * 查询景区详细信息 含门票
     * @param id 景区id
     * @param longitude 经度
     * @param latitude 维度
     * @return 景区详细
     */
    ScenicVO detailById(Long id, Double longitude, Double latitude);

}
