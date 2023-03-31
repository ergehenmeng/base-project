package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Line;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.vo.business.line.LineListVO;
import org.apache.ibatis.annotations.Param;

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
     * 分页查询线路列表
     * @param page 分页信息 不含总页数及总条数
     * @param dto 查询条件
     * @return 线路列表
     */
    Page<LineListVO> getByPage(Page<LineListVO> page, @Param("param") LineQueryDTO dto);
}
