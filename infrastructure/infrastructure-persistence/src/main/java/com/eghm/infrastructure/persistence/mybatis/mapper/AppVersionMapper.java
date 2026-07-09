package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.AppVersionPO;
import com.eghm.vo.operate.version.AppVersionResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface AppVersionMapper extends BaseMapper<AppVersionPO> {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param request 查询条件
     * @return 分页结果
     */
    Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, @Param("param") VersionQueryRequest request);

    /**
     * 获取已上架版本的信息说明
     *
     * @param channel app类型
     * @return 版本信息
     */
    AppVersionPO getVersion(@Param("channel") String channel);

    /**
     * 在指定时间段内获取强制更新的版本列表
     *
     * @param channel      app类型
     * @param startVersion 开始版本
     * @param endVersion   结束版本
     * @return 强制更新的版本列表
     */
    List<AppVersionPO> getForceUpdateVersion(@Param("channel") String channel,
                                             @Param("startVersion") Integer startVersion,
                                             @Param("endVersion") Integer endVersion);
}
