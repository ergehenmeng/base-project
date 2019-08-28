package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.HelpCenter;
import com.fanyin.dao.model.business.HelpInstruction;
import com.fanyin.model.dto.business.help.HelpQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface HelpCenterMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(HelpCenter record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    HelpCenter selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(HelpCenter record);

    /**
     * 根据条件查询帮助说明
     * @param request 查询条件
     * @return 列表
     */
    List<HelpCenter> getList(HelpQueryRequest request);
}