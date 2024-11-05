package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.help.HelpQueryRequest;
import com.eghm.model.HelpCenter;
import com.eghm.vo.help.HelpResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface HelpCenterMapper extends BaseMapper<HelpCenter> {

    /**
     * 分页查询
     * @param page 分页参数
     * @param request 查询条件
     * @return list
     */
    Page<HelpResponse> getByPage(Page<HelpResponse> page, @Param("param") HelpQueryRequest request);

    /**
     * 按条件查询帮助信息
     *
     * @param helpType  分类
     * @param queryName 关键字查询
     * @return list 不包含删除的和不显示的,且排序好了
     */
    List<HelpCenter> getList(@Param("helpType") Integer helpType, @Param("queryName") String queryName);
}