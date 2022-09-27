package com.eghm.service.sys;

import com.eghm.model.SysArea;
import com.eghm.model.vo.sys.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
public interface SysAreaService {

    /**
     * 计算首字母
     */
    void calcInitial();

    /**
     * 查询子级的地址列表
     * @param pid 当前级地址id
     * @return list
     */
    List<SysAreaVO> getByPid(Long pid);

    /**
     * 主键查询
     * @param id id
     * @return 地区
     */
    SysArea getById(Long id);

    /**
     * 根据省市县id进行拼接
     * @param provinceId 省份id
     * @param cityId 城市id
     * @param countyId 县区id
     * @return 浙江省杭州市西湖区
     */
    String parseArea(Long provinceId, Long cityId, Long countyId);

    /**
     * 根据省市县id进行拼接
     * @param cityId 城市id
     * @param countyId 县区id
     * @return 杭州市西湖区
     */
    String parseArea(Long cityId, Long countyId);
}

