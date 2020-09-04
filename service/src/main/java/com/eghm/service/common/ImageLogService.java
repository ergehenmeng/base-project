package com.eghm.service.common;

import com.eghm.model.dto.image.ImageAddRequest;
import com.eghm.model.dto.image.ImageEditRequest;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.dao.model.ImageLog;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:11
 */
public interface ImageLogService {

    /**
     * 分页查询图片上传列表
     * @param request 查询条件
     * @return 结果
     */
    PageInfo<ImageLog> getByPage(ImageQueryRequest request);

    /**
     * 添加图片记录
     * @param request 前台参数
     */
    void addImageLog(ImageAddRequest request);

    /**
     * 删除图片记录信息
     * @param id id
     */
    void deleteImageLog(Integer id);

    /**
     * 更新图片信息
     * @param request 前台参数
     */
    void updateImageLog(ImageEditRequest request);

    /**
     * 根据主键查询
     * @param id 主键
     * @return 图片上传记录
     */
    ImageLog getById(Integer id);
}

