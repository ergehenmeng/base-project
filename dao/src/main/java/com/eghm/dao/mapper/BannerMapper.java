package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.Banner;
import com.eghm.model.dto.banner.BannerQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BannerMapper extends BaseMapper<Banner> {

    /**
     * 根据模块类型及客户单类型查询轮播图列表,只查询非过期的轮播图
     * @param classify 模块类型由dict表维护
     * @param clientType 客户单类型
     * @return 轮播图列表
     */
    List<Banner> getBannerList(@Param("classify") Byte classify, @Param("clientType") String clientType);

    /**
     * 根据条件查询轮播图信息
     * @param request 查询条件
     * @return 列表
     */
    List<Banner> getList(BannerQueryRequest request);

}