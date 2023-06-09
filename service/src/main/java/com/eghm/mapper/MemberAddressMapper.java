package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.MemberAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface MemberAddressMapper extends BaseMapper<MemberAddress> {

    /**
     * 获取用户地址信息
     * @param memberId memberId
     * @return 地址列表
     */
    List<MemberAddress> getByMemberId(@Param("memberId")Long memberId);

    /**
     * 更新地址状态
     * @param memberId memberId
     * @param state 状态
     * @return 批量多少条
     */
    int updateState(@Param("memberId")Long memberId, @Param("state")Integer state);

    /**
     * 主键+memberId更新地址
     * @param address address
     * @return 1
     */
    int updateByMemberId(MemberAddress address);
}