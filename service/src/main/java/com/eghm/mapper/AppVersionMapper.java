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
     * 获取已上架版本的信息说明
     *
     * @param channel app类型
     * @param version  版本号
     * @return 版本信息
     */
    AppVersion getVersion(@Param("channel") String channel, @Param("version") String version);

    /**
     * 在指定时间段内获取强制更新的版本列表
     *
     * @param channel     app类型
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 强制更新的版本列表
     */
    List<AppVersion> getForceUpdateVersion(@Param("channel") String channel,
                                           @Param("startVersion") Integer startVersion,
                                           @Param("endVersion") Integer endVersion);
}