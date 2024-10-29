package com.eghm.service.sys;

import com.eghm.dto.dict.*;
import com.eghm.model.SysDict;
import com.eghm.model.SysDictItem;
import com.eghm.vo.sys.DictResponse;

import java.util.List;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @since 2018/1/12 14:31
 */
public interface SysDictService {

    /**
     * 根据条件查询数据字典信息(不分页)
     *
     * @param request 前台条件
     * @return 列表
     */
    List<DictResponse> getList(DictQueryRequest request);

    /**
     * 根据nid查询某一类数据字典列表
     *
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    List<SysDictItem> getDictByNid(String nid);

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     */
    void create(DictAddRequest request);

    /**
     * 编辑数据字典
     *
     * @param request 前台参数
     */
    void update(DictEditRequest request);

    /**
     * 删除数据字典
     *
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 添加数据字典子选项
     *
     * @param request 前台参数
     */
    void itemCreate(DictItemAddRequest request);

    /**
     * 编辑数据字典子选项
     *
     * @param request 前台参数
     */
    void itemUpdate(DictItemEditRequest request);

    /**
     * 删除数据字典子选项
     *
     * @param id 主键
     */
    void itemDelete(Long id);

    /**
     * 根据nid与隐藏值获取显示信息 数据字典格式化数据
     *
     * @param nid         nid
     * @param hiddenValue 隐藏值
     * @return 显示值
     */
    String getDictValue(String nid, Integer hiddenValue);

    /**
     * 批量查询字典标签
     *
     * @param nid    标签分类标识符
     * @param tagIds 一系列标签 逗号分割
     * @return 标签列表
     */
    List<String> getTags(String nid, String tagIds);
}
