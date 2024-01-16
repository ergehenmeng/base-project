package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface AppVersionMapper extends BaseMapper<AppVersion> {

    /**
     * 获取最新已上架的版本信息
     *
     * @param classify App类型 ANDROID IOS
     * @return 版本信息
     */
    AppVersion getLatestVersion(@Param("classify") String classify);

    /**
     * 获取已上架版本的信息说明
     *
     * @param classify app类型
     * @param version  版本号
     * @return 版本信息
     */
    AppVersion getVersion(@Param("classify") String classify, @Param("version") String version);

    /**
     * 在指定时间段内获取强制更新的版本列表
     *
     * @param classify     app类型
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 强制更新的版本列表
     */
    List<AppVersion> getForceUpdateVersion(@Param("classify") String classify,
                                           @Param("startVersion") Integer startVersion,
                                           @Param("endVersion") Integer endVersion);
}