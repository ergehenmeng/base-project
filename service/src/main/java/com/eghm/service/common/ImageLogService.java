package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.image.ImageAddRequest;
import com.eghm.dto.sys.image.ImageEditRequest;
import com.eghm.dto.sys.image.ImageQueryRequest;
import com.eghm.model.ImageLog;
import com.eghm.vo.log.ImageLogResponse;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
public interface ImageLogService {

    /**
     * 分页查询图片上传列表
     *
     * @param request 查询条件
     * @return 结果
     */
    Page<ImageLogResponse> getByPage(ImageQueryRequest request);

    /**
     * 添加图片记录
     *
     * @param request 前台参数
     */
    void create(ImageAddRequest request);

    /**
     * 删除图片记录信息
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 更新图片信息
     *
     * @param request 前台参数
     */
    void update(ImageEditRequest request);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 图片上传记录
     */
    ImageLog getById(Long id);
}

