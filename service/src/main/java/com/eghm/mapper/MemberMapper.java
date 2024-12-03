package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.dto.business.member.MemberQueryRequest;
import com.eghm.model.Member;
import com.eghm.vo.business.statistics.MemberChannelVO;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.statistics.MemberSexVO;
import com.eghm.vo.business.member.MemberResponse;
import org.apache.ibatis.annotations.Param;


import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 分页查询会员
     *
     * @param page    分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<MemberResponse> listPage(Page<MemberResponse> page, @Param("param") MemberQueryRequest request);

    /**
     * 注册统计(渠道)
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return  注册数量
     */
    List<MemberChannelVO> channelStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 注册统计(性别)
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return  注册数量
     */
    List<MemberSexVO> sexStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 注册统计 按天或按月
     *
     * @param request 查询条件
     * @return 注册数量
     */
    List<MemberRegisterVO> dayRegister(DateRequest request);
}