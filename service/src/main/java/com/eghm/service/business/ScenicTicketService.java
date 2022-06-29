package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.model.dto.ticket.ScenicTicketAddRequest;
import com.eghm.model.dto.ticket.ScenicTicketEditRequest;
import com.eghm.model.dto.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.ticket.ScenicTicketResponse;

/**
 * @author 二哥很猛 2022/6/15 21:11
 */
public interface ScenicTicketService {

    /**
     * 分页查询门票信息
     * @param request 查询条件
     * @return 门票信息
     */
    Page<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request);

    /**
     * 创建景区门票
     * @param request 门票信息
     */
    void createTicket(ScenicTicketAddRequest request);

    /**
     * 更新景区门票信息
     * @param request 门票信息
     */
    void updateTicket(ScenicTicketEditRequest request);

    /**
     * 主键查询
     * @param id id
     * @return 景区信息
     */
    ScenicTicket selectById(Long id);

    /**
     * 更新门票上下状态
     * @param id id
     * @param state 新状态 0:待上架 1: 已上架
     */
    void updateState(Long id, Integer state);
}
