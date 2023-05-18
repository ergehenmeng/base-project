package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.ScenicTicket;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.vo.scenic.ticket.TicketBaseVO;
import com.eghm.vo.scenic.ticket.TicketVO;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/15
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
     * 查询门票
     * @param id 主键
     * @return 景区门票信息 为空时则会报错
     */
    ScenicTicket selectByIdRequired(Long id);

    /**
     * 查询上架的门票
     * @param id 主键
     * @return 景区门票信息 为空或没有上架时都会报错
     */
    ScenicTicket selectByIdShelve(Long id);

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

    /**
     * 更新门票库存信息
     * @param id id
     * @param num 更新数量 负数-库存 正数+库存
     */
    void updateStock(Long id, Integer num);

    /**
     * 删除门票
     * @param id id
     */
    void deleteById(Long id);
}
