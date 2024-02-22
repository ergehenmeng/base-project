package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ref.State;
import com.eghm.model.ScenicTicket;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.vo.business.scenic.ticket.TicketVO;

/**
 * @author 二哥很猛 2022/6/15
 */
public interface ScenicTicketService {

    /**
     * 分页查询门票信息
     *
     * @param request 查询条件
     * @return 门票信息
     */
    Page<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request);

    /**
     * 创建景区门票
     *
     * @param request 门票信息
     */
    void createTicket(ScenicTicketAddRequest request);

    /**
     * 更新景区门票信息
     *
     * @param request 门票信息
     */
    void updateTicket(ScenicTicketEditRequest request);

    /**
     * 查询门票
     *
     * @param id 主键
     * @return 景区门票信息 为空时则会报错
     */
    ScenicTicket selectByIdRequired(Long id);

    /**
     * 查询上架的门票
     *
     * @param id 主键
     * @return 景区门票信息 为空或没有上架时都会报错
     */
    ScenicTicket selectByIdShelve(Long id);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 查询门票详细信息
     *
     * @param id id
     * @return 详细信息
     */
    TicketVO detailById(Long id);

    /**
     * 更新门票库存信息
     *
     * @param id  id
     * @param num 更新数量 负数-库存 正数+库存
     */
    void updateStock(Long id, Integer num);

    /**
     * 删除门票
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新景区评分
     *
     * @param vo 评分信息
     */
    void updateScore(CalcStatistics vo);

    /**
     * 分页查询商品列表
     *
     * @param request 查询条件
     * @return 基础信息
     */
    Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request);
}
