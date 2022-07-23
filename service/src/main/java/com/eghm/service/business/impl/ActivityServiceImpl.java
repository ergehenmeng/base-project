package com.eghm.service.business.impl;

import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.ActivityMapper;
import com.eghm.dao.model.Activity;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.service.business.ActivityService;
import com.eghm.service.business.CommonService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@Service("activityService")
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

    private final CommonService commonService;

    @Override
    public void create(ActivityAddRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        commonService.checkMaxDay(ConfigConstant.ACTIVITY_CONFIG_MAX_DAY, between);

        for (int i = 0; i < between; i++) {
            LocalDate plusDays = request.getStartDate().plusDays(i);
            if (!request.getWeek().contains(plusDays.getDayOfWeek().getValue())) {
                continue;
            }
            Activity activity = DataUtil.copy(request, Activity.class);
            activity.setNowDate(plusDays);
            activityMapper.insert(activity);
        }
    }

    @Override
    public void update(ActivityEditRequest request) {
        Activity activity = DataUtil.copy(request, Activity.class);
        activityMapper.updateById(activity);
    }
}
