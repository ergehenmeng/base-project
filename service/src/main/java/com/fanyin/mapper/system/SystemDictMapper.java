package com.fanyin.mapper.system;


import com.fanyin.dto.system.dict.DictQueryRequest;
import com.fanyin.model.system.SystemDict;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemDictMapper {


    /**
     * 插入不为空的记录
     *
     * @param record 待插入的对象
     * @return 添加的条数
     */
    int insertSelective(SystemDict record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 主键
     * @return 查询到的数据字典对象
     */
    SystemDict selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 待更新的对象
     * @return 更新成功的条数
     */
    int updateByPrimaryKeySelective(SystemDict record);

    /**
     * 根据nid查询某一类数据字典列表
     * @param nid 某类数据字典nid
     * @return 查询到的列表
     */
    List<SystemDict> getDictByNid(String nid);

    /**
     * 根据条件查询数据字典列表
     * @param request 查询条件
     * @return 列表
     */
    List<SystemDict> getList(DictQueryRequest request);

    /**
     * 根据主键选择更新数据字典信息
     * @param dict 前台参数
     * @return 影响条数
     */
    int updateByIdSelective(SystemDict dict);
}