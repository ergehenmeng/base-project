package com.eghm.service.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.member.MemberScoreQueryDTO;
import com.eghm.dto.member.MemberScoreQueryRequest;
import com.eghm.model.MemberScoreLog;
import com.eghm.vo.member.MemberScoreVO;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
public interface MemberScoreLogService {

    /**
     * 分页查询用户积分列表
     *
     * @param request 查询条件
     * @return 积分列表
     */
    Page<MemberScoreVO> getByPage(MemberScoreQueryRequest request);

    /**
     * 分页查询用户积分列表
     *
     * @param request 查询条件
     * @return 积分列表
     */
    List<MemberScoreVO> clientByPage(MemberScoreQueryDTO request);

    /**
     * 添加积分信息
     *
     * @param scoreLog 积分
     */
    void insert(MemberScoreLog scoreLog);

    /**
     * 获取每日签到积分数 (随机,且由系统参数影响)
     *
     * @return 积分数
     */
    int getSignInScore();
}
