package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.ScenicTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.ticket.ScenicTicketResponse;

/**
 * <p>
 * 景区门票信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
public interface ScenicTicketMapper extends BaseMapper<ScenicTicket> {

    /**
     * 分页查询门票信息(管理后台)
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ScenicTicketResponse> getByPage(Page<ScenicTicketResponse> page, ScenicTicketQueryRequest request);
}
