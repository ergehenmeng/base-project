package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.HelpCenter;
import com.eghm.model.dto.help.HelpQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface HelpCenterMapper extends BaseMapper<HelpCenter> {

    /**
     * 根据条件查询帮助说明
     * @param request 查询条件
     * @return 列表
     */
    List<HelpCenter> getList(HelpQueryRequest request);

    /**
     * 按条件查询帮助信息
     * @param classify 分类
     * @param queryName 关键字查询
     * @return list 不包含删除的和不显示的,且排序好了
     */
    List<HelpCenter> getListSorted(@Param("classify") Byte classify, @Param("queryName") String queryName);
}