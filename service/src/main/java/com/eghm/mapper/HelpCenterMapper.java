package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.HelpCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface HelpCenterMapper extends BaseMapper<HelpCenter> {

    /**
     * 按条件查询帮助信息
     *
     * @param helpType  分类
     * @param queryName 关键字查询
     * @return list 不包含删除的和不显示的,且排序好了
     */
    List<HelpCenter> getList(@Param("helpType") Integer helpType, @Param("queryName") String queryName);
}