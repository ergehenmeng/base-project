package com.eghm.common;

import com.eghm.model.SysDictItem;
import com.eghm.vo.sys.ext.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
public interface CommonService {

    /**
     * 根据给定的字典列表和标签id进行解析
     *
     * @param dictList 字典列表
     * @param tagIds   标签id 逗号分割
     * @return 标签名称
     */
    List<String> parseTags(List<SysDictItem> dictList, String tagIds);

    /**
     * 查询地址列表(树状结构)
     *
     * @return list
     */
    List<SysAreaVO> getTreeAreaList();

    /**
     * 查询地址列表(树状结构)
     *
     * @param gradeList 省份1级 市2级 县3级
     * @return list
     */
    List<SysAreaVO> getTreeAreaList(List<Integer> gradeList);
}
