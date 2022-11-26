package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.ScenicTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.model.vo.scenic.ticket.TicketBaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    Page<ScenicTicketResponse> getByPage(Page<ScenicTicketResponse> page, @Param("param") ScenicTicketQueryRequest request);

    /**
     * 景区下在售门票信息
     * @param scenicId 景区id
     * @return 门票信息
     */
    List<TicketBaseVO> getTicketList(@Param("scenicId") Long scenicId);

    /**
     * 更新库存
     * @param id 优惠券id
     * @param num 数量 负数-库存 正数+库存
     */
    int updateStock(@Param("id") Long id, @Param("num") int num);
}
