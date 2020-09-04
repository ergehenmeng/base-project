package com.eghm.dao.mapper;


import com.eghm.model.dto.help.HelpQueryRequest;
import com.eghm.dao.model.HelpInstruction;

import java.util.List;


/**
 * @author 二哥很猛
 */
public interface HelpInstructionMapper {

    /**
     * 插入不为空的记录
     */
    int insertSelective(HelpInstruction record);

    /**
     * 根据主键获取一条数据库记录
     */
    HelpInstruction selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     */
    int updateByPrimaryKeySelective(HelpInstruction record);

    /**
     * 根据条件查询帮助说明
     * @param request 查询条件
     * @return 列表
     */
    List<HelpInstruction> getList(HelpQueryRequest request);
}