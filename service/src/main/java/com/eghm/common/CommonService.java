package com.eghm.common;

import com.eghm.vo.sys.ext.SysAreaVO;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
public interface CommonService {

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

    /**
     * 点赞或取消点赞
     *
     * @param key      key
     * @param hashKey  value
     * @param consumer 后置处理 true:点赞 false:取消点赞
     */
    void praise(String key, String hashKey, Consumer<Boolean> consumer);
}
