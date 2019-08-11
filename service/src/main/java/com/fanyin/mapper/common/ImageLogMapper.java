package com.fanyin.mapper.common;

import com.fanyin.dto.common.image.ImageQueryRequest;
import com.fanyin.model.common.ImageLog;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface ImageLogMapper {


    /**
     * 插入不为空的记录
     */
    int insertSelective(ImageLog record);

    /**
     * 根据主键获取一条数据库记录
     */
    ImageLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     */
    int updateByPrimaryKeySelective(ImageLog record);


    /**
     * 根据条件查询图片列表
     * @param request 请求
     * @return 列表
     */
    List<ImageLog> getList(ImageQueryRequest request);
}