package com.eghm.service.business.impl;

import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.mapper.TicketOrderSnapshotMapper;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrderSnapshot;
import com.eghm.service.business.TicketOrderSnapshotService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合票订单表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class TicketOrderSnapshotServiceImpl implements TicketOrderSnapshotService {

    private final ScenicTicketMapper scenicTicketMapper;

    private final TicketOrderSnapshotMapper ticketOrderSnapshotMapper;

    @Override
    public void insert(String orderNo, Long ticketId) {
        List<ScenicTicket> ticketList = scenicTicketMapper.getCombineList(ticketId);
        ticketList.forEach(ticket -> {
            TicketOrderSnapshot snapshot = DataUtil.copy(ticket, TicketOrderSnapshot.class, "id");
            snapshot.setOrderNo(orderNo);
            snapshot.setTicketId(ticket.getId());
            ticketOrderSnapshotMapper.insert(snapshot);
        });
    }
}
