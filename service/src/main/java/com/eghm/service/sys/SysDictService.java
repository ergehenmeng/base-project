package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysDict;
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

    /**
     * 根据nid与隐藏值获取显示信息 数据字典格式化数据
     *
     * @param nid         nid
     * @param hiddenValue 隐藏值
     * @return 显示值
     */
    String getDictValue(String nid, Byte hiddenValue);

    /**
     * 批量查询字典标签
     * @param nid 标签分类标识符
     * @param tagIds 一系列标签 逗号分割
     * @return 标签列表
     */
    List<String> getTags(String nid, String tagIds);
}
