package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.AppVersion;
import com.eghm.model.dto.version.VersionQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface AppVersionMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(AppVersion record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    AppVersion selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(AppVersion record);

    /**
     * 根据条件查询app管理列表
     * @param request 查询条件
     * @return 列表
     */
    List<AppVersion> getList(VersionQueryRequest request);

    /**
     * 获取最新已上架的版本信息
     * @param classify App类型 ANDROID IOS
     * @return 版本信息
     */
    AppVersion getLatestVersion(@Param("classify")String classify);

    /**
     * 获取已上架版本的信息说明
     * @param classify app类型
     * @param version 版本号
     * @return 版本信息
     */
    AppVersion getVersion(@Param("classify")String classify, @Param("version")String version);

    /**
     * 在指定时间段内获取强制更新的版本列表
     * @param classify app类型
     * @param startVersion 开始版本
     * @param endVersion 结束版本
     * @return 强制更新的版本列表
     */
    List<AppVersion> getForceUpdateVersion(@Param("classify")String classify,
                                           @Param("startVersion")Integer startVersion,
                                           @Param("endVersion")Integer endVersion);
}