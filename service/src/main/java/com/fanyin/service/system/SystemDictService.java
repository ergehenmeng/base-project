package com.fanyin.service.system;

import com.fanyin.dto.system.dict.DictAddRequest;
import com.fanyin.dto.system.dict.DictEditRequest;
import com.fanyin.dto.system.dict.DictQueryRequest;
import com.fanyin.model.system.SystemDict;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 数据字典服务类
 * @author 二哥很猛
 * @date 2018/1/12 14:31
 */
public interface SystemDictService {

    /**
     * 根据nid查询某一类数据字典列表
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    List<SystemDict> getDictByNid(String nid);

    /**
     * 根据条件分页查询数据字典信息
     * @param request 前台cax条件
     * @return 分页列表
     */
    PageInfo<SystemDict> getByPage(DictQueryRequest request);

    /**
     * 添加数据字典
     * @param request 前台参数
     */
    void addDict(DictAddRequest request);


    /**
     * 编辑数据字典
     * @param request 前台参数
     */
    void updateDict(DictEditRequest request);

    /**
     * 删除数据字典
     * @param id 主键
     */
    void deleteDict(Integer id);

    /**
     * 根据主键查询
     * @param id 主键
     * @return 数据字典
     */
    SystemDict getById(Integer id);
}
