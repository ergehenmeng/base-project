package com.eghm.service.business.impl;

import com.eghm.dao.mapper.ActivityMapper;
import com.eghm.service.business.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@Service("activityService")
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

}
