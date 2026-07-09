package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.dto.business.member.MemberQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.MemberPO;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.member.MemberResponse;
import com.eghm.vo.business.statistics.PieDataVO;
import org.apache.ibatis.annotations.Param;


import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 */
public interface MemberMapper extends BaseMapper<MemberPO> {

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
     * @param endDate   结束日期
     * @return 注册数量
     */
    List<PieDataVO> channelStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 注册统计(性别)
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 注册数量
     */
    List<PieDataVO> sexStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 注册统计 按天或按月
     *
     * @param request 查询条件
     * @return 注册数量
     */
    List<MemberRegisterVO> dayRegister(DateRequest request);

    /**
     * 查询会员手机号
     *
     * @param memberIds 会员id
     * @return 手机号
     */
    List<String> getMobile(@Param("memberIds") List<Long> memberIds);

    /**
     * 更新会员积分
     *
     * @param memberId 用户id
     * @param score 积分数量
     * @return 1
     */
    int updateScore(@Param("memberId") Long memberId, @Param("score") Integer score);
}
