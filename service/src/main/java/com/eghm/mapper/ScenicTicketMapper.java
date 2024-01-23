package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.model.ScenicTicket;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.vo.business.scenic.ticket.TicketBaseVO;
import com.eghm.vo.business.scenic.ticket.TicketPriceVO;
import com.eghm.vo.business.statistics.ProductStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ScenicTicketResponse> getByPage(Page<ScenicTicketResponse> page, @Param("param") ScenicTicketQueryRequest request);

    /**
     * 景区下在售门票信息
     *
     * @param scenicId 景区id
     * @return 门票信息
     */
    List<TicketBaseVO> getTicketList(@Param("scenicId") Long scenicId);

    /**
     * 更新库存
     *
     * @param id  优惠券id
     * @param num 数量 负数-库存 正数+库存
     */
    int updateStock(@Param("id") Long id, @Param("num") int num);

    /**
     * 计算景区最低价和最高价
     *
     * @param scenicId 景区id
     * @return 最高价, 最低价
     */
    TicketPriceVO calcPrice(@Param("scenicId") Long scenicId);

    /**
     * 根据门票id更新所属景区的评分
     *
     * @param id    门票id
     * @param score 景区评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 新增商品列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<ProductStatisticsVO> dayAppend(ProductRequest request);
}
