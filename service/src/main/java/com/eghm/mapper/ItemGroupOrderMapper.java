package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ItemGroupOrder;
import com.eghm.vo.business.group.GroupMemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 拼团订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-24
 */
public interface ItemGroupOrderMapper extends BaseMapper<ItemGroupOrder> {

    /**
     * 获取拼团成员
     *
     * @param bookingNo 拼团订单号
     * @return 拼团成员
     */
    List<GroupMemberVO> getMemberList(@Param("bookingNo") String bookingNo);
}
