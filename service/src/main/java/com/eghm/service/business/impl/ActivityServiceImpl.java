package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.ActivityMapper;
import com.eghm.dao.model.Activity;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityConfigRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.model.vo.business.activity.ActivityBaseResponse;
import com.eghm.service.business.ActivityService;
import com.eghm.service.business.CommonService;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
    public void create(ActivityConfigRequest request) {
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
    public void create(ActivityAddRequest request) {
        Activity activity = DataUtil.copy(request, Activity.class);
        activityMapper.insert(activity);
    }

    @Override
    public void update(ActivityEditRequest request) {
        Activity activity = DataUtil.copy(request, Activity.class);
        activityMapper.updateById(activity);
    }

    @Override
    public List<ActivityBaseResponse> getMonthActivity(String month) {
        LocalDate startDate = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = startDate.plusMonths(1);
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.ge(Activity::getNowDate, startDate);
        wrapper.lt(Activity::getNowDate, endDate);
        wrapper.last(" order by id desc ");
        List<Activity> selectList = activityMapper.selectList(wrapper);
        int dayOfMonth = startDate.lengthOfMonth();
        List<ActivityBaseResponse> responseList = Lists.newArrayListWithExpectedSize(31);

        for (int i = 0; i < dayOfMonth; i++) {
            ActivityBaseResponse response = new ActivityBaseResponse();
            response.setNowDate(startDate.plusDays(i));
            response.setActivityList(this.filterDateActivity(selectList, response.getNowDate()));
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public Activity selectById(Long id) {
        return activityMapper.selectById(id);
    }

    @Override
    public void deleteById(Long id) {
        activityMapper.deleteById(id);
    }

    /**
     * 过滤某一天的活动并进行对象映射
     * @param selectList 活动列表
     * @param date 天 yyyy-MM-dd
     * @return 该天的活动 倒叙列表
     */
    private List<ActivityBaseResponse.ActivityResponse> filterDateActivity(List<Activity> selectList, LocalDate date) {
        return selectList.stream().filter(activityCalendar -> activityCalendar.getNowDate().isEqual(date))
                .map(activityCalendar -> DataUtil.copy(activityCalendar, ActivityBaseResponse.ActivityResponse.class))
                .collect(Collectors.toList());
    }
}
