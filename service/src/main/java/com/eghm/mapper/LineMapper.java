package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.model.Line;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.vo.business.line.LineResponse;
import com.eghm.vo.business.line.LineVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 线路商品信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
public interface LineMapper extends BaseMapper<Line> {

    /**
     * 分页查询线路列表(manage)
     * @param page 分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<LineResponse> listPage(Page<LineResponse> page, @Param("param") LineQueryRequest request);

    /**
     * 分页查询线路列表(webapp)
     * @param page 分页信息 不含总页数及总条数
     * @param dto 查询条件
     * @return 线路列表
     */
    Page<LineVO> getByPage(Page<LineVO> page, @Param("param") LineQueryDTO dto);

    /**
     * 更新评分
     *
     * @param id id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);
}
