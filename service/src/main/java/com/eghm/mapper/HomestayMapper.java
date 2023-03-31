package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Homestay;
import com.eghm.dto.business.homestay.HomestayQueryDTO;
import com.eghm.vo.business.homestay.HomestayListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 民宿信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
public interface HomestayMapper extends BaseMapper<Homestay> {

    /**
     * 分页查询民宿列表
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<HomestayListVO> getByPage(Page<HomestayListVO> page, @Param("param") HomestayQueryDTO dto);

}
