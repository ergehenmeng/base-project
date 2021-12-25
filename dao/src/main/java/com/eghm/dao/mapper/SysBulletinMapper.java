package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.bulletin.BulletinQueryRequest;
import com.eghm.dao.model.SysBulletin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysBulletinMapper extends BaseMapper<SysBulletin> {

    /**
     * 获取前多少条公告信息
     * @param noticeLimit 几条公告
     * @return 公告列表
     */
    List<SysBulletin> getTopList(@Param("noticeLimit") int noticeLimit);
}