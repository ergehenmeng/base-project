package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.model.Banner;
import com.eghm.vo.banner.BannerResponse;
import com.eghm.vo.banner.BannerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BannerMapper extends BaseMapper<Banner> {

    /**
     * 分页查询
     *
     * @param page 分页参数
     * @param request 查询参数
     * @return 列表
     */
    Page<BannerResponse> getByPage(Page<BannerResponse> page, @Param("param") BannerQueryRequest request);

    /**
     * 根据模块类型及客户单类型查询轮播图列表,只查询非过期的轮播图
     *
     * @param bannerType   模块类型由dict表维护
     * @param clientType 客户单类型
     * @return 轮播图列表
     */
    List<BannerVO> getBannerList(@Param("bannerType") Integer bannerType, @Param("clientType") String clientType);

}