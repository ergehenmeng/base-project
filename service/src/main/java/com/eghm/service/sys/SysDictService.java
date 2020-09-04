package com.eghm.service.sys;

import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.dao.model.SysDict;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 数据字典服务类
 * @author 二哥很猛
 * @date 2018/1/12 14:31
 */
public interface SysDictService {

    /**
     * 根据nid查询某一类数据字典列表
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    List<SysDict> getDictByNid(String nid);

    /**
     * 根据条件分页查询数据字典信息
     * @param request 前台cax条件
     * @return 分页列表
     */
    PageInfo<SysDict> getByPage(DictQueryRequest request);

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
    SysDict getById(Integer id);
}
