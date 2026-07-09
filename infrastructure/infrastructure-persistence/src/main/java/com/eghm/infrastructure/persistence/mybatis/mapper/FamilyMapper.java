package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.eghm.infrastructure.persistence.mybatis.po.FamilyPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.sys.family.FamilyResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 族谱信息表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-12-15
 */
public interface FamilyMapper extends BaseMapper<FamilyPO> {

    /**
     * 查询家族所有成员
     *
     * @return 成员列表
     */
    List<FamilyResponse> getList();

    /**
     * 查询某个菜单下的最大子菜单id
     *
     * @param pid pid
     * @return 最大子菜单id
     */
    String getMaxId(@Param("pid") String pid);
}

