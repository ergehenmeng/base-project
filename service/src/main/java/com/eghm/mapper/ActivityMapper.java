package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.Activity;
import com.eghm.vo.business.activity.ActivityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 活动信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-18
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 获取活动信息
     *
     * @param activityIds 活动id
     * @return 活动信息
     */
    List<ActivityVO> getList(@Param("activityIds") List<Long> activityIds);
}
