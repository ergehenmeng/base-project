package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 获取前多少条公告信息
     * @param noticeLimit 几条公告
     * @return 公告列表
     */
    List<SysNotice> getTopList(@Param("noticeLimit") int noticeLimit);
}