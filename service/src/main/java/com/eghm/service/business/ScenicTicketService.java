package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.model.vo.scenic.ticket.TicketBaseVO;
import com.eghm.model.vo.scenic.ticket.TicketVO;

import java.util.List;

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
    void updateAuditState(Long id, AuditState state);

    /**
     * 查询景区下的在售门票的基本信息
     * 售罄,删除,未上架,已过销售期的门票都不显示
     * @param scenicId 景区id
     * @return 门票
     */
    List<TicketBaseVO> getTicketList(Long scenicId);

    /**
     * 查询门票详细信息
     * @param id id
     * @return 详细信息
     */
    TicketVO detailById(Long id);
}
