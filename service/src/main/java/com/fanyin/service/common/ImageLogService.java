package com.fanyin.service.common;

import com.fanyin.dto.common.image.ImageAddRequest;
import com.fanyin.dto.common.image.ImageEditRequest;
import com.fanyin.dto.common.image.ImageQueryRequest;
import com.fanyin.model.common.ImageLog;
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

