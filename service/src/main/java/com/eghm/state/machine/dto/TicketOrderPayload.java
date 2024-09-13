package com.eghm.state.machine.dto;

import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import lombok.Data;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
public class TicketOrderPayload {

    /**
     * 门票信息
     */
    private ScenicTicket ticket;

    /**
     * 景区信息
     */
    private Scenic scenic;
}
