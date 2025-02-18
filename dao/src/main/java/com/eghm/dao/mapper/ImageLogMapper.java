package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.dao.model.ImageLog;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface ImageLogMapper extends BaseMapper<ImageLog> {

    /**
     * 根据条件查询图片列表
     * @param request 请求
     * @return 列表
     */
    List<ImageLog> getList(ImageQueryRequest request);
}