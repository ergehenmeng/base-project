package com.eghm.platform.config.service;

import com.eghm.platform.config.dto.*;
import com.eghm.platform.config.entity.SysDictItem;
import com.eghm.platform.config.vo.DictResponse;
import com.eghm.foundation.core.service.DictOptionProvider;

import java.util.List;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @since 2018/1/12 14:31
 */
public interface SysDictService extends DictOptionProvider {

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

    @Override
    default String[] getOptions(String key) {
        return getDictByNid(key).stream().map(SysDictItem::getShowValue).toArray(String[]::new);
    }

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

}
