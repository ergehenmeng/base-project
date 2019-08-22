package com.fanyin.dao.mapper.common;

import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BannerMapper {

    /**
     * 插入不为空的记录
     * @param record 新增对象
     * @return 影响条数
     */
    int insertSelective(Banner record);

    /**
     * 根据主键获取一条数据库记录
     * @param id 主键
     * @return 主键查询对象
     */
    Banner selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     * @param record 不为空更新对象
     * @return 影响条数
     */
    int updateByPrimaryKeySelective(Banner record);

    /**
     * 根据模块类型及客户单类型查询轮播图列表,只查询非过期的轮播图
     * @param classify 模块类型由dict表维护
     * @param clientType 客户单类型
     * @return 轮播图列表
     */
    List<Banner> getBannerList(@Param("classify") Byte classify, @Param("clientType") Byte clientType);

    /**
     * 根据条件查询轮播图信息
     * @param request 查询条件
     * @return 列表
     */
    List<Banner> getList(BannerQueryRequest request);

}