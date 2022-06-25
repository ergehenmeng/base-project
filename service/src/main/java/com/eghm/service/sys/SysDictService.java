package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;

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
    Page<SysDict> getByPage(DictQueryRequest request);

    /**
     * 添加数据字典
     * @param request 前台参数
     */
    void create(DictAddRequest request);

    /**
     * 编辑数据字典
     * @param request 前台参数
     */
    void update(DictEditRequest request);

    /**
     * 删除数据字典
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 根据主键查询
     * @param id 主键
     * @return 数据字典
     */
    SysDict getById(Long id);
}
