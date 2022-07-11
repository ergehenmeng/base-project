package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Scenic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.business.scenic.ScenicQueryDTO;
import com.eghm.model.vo.scenic.ScenicListVO;

import java.util.List;

/**
 * <p>
 * 景区信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
public interface ScenicMapper extends BaseMapper<Scenic> {

    /**
     * 查询景区列表
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<ScenicListVO> getByPage(Page<ScenicListVO> page, ScenicQueryDTO dto);
}
