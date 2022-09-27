package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.Banner;
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

}