package com.eghm.service.business.impl;

import com.eghm.dao.mapper.ScenicTicketMapper;
import com.eghm.service.business.ScenicTicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wyb 2022/6/15 21:11
 */
@Service("scenicTicketService")
@AllArgsConstructor
public class ScenicTicketServiceImpl implements ScenicTicketService {

    private final ScenicTicketMapper scenicTicketMapper;

}
