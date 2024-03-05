package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ItemTag;
import com.eghm.vo.business.item.ItemTagResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 零售标签 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-08
 */
public interface ItemTagMapper extends BaseMapper<ItemTag> {

    /**
     * 查询所有标签
     *
     * @return list
     */
    List<ItemTagResponse> getList();

    /**
     * 查询某个pid下的最大id
     *
     * @param pid pid
     * @return 最大值
     */
    String getChildMaxId(@Param("pid") String pid);
}
