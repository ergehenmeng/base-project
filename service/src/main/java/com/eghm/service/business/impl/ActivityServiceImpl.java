package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.ActivityMapper;
import com.eghm.model.Activity;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityConfigRequest;
import com.eghm.model.dto.business.activity.ActivityDeleteRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.model.vo.business.activity.ActivityBaseDTO;
import com.eghm.model.vo.business.activity.ActivityBaseResponse;
import com.eghm.service.business.ActivityService;
import com.eghm.service.business.CommonService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

    private final CommonService commonService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void create(ActivityConfigRequest request) {
        this.redoTitle(request.getTitle(), request.getStartDate(), request.getEndDate());
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
        this.redoTitle(request.getTitle(), (Long) null, request.getNowDate());
        Activity activity = DataUtil.copy(request, Activity.class);
        activityMapper.insert(activity);
    }

    @Override
    public void update(ActivityEditRequest request) {
        // 同一天活动名称不能重复
        Activity select = activityMapper.selectById(request.getId());
        this.redoTitle(request.getTitle(), request.getId(), select.getNowDate());

        Activity activity = DataUtil.copy(request, Activity.class);
        activityMapper.updateById(activity);
    }

    @Override
    public List<ActivityBaseResponse> getMonthActivity(String month, Long scenicId) {
        LocalDate startDate = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = startDate.plusMonths(1);
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.isNull(scenicId == null, Activity::getScenicId);
        wrapper.eq(scenicId != null, Activity::getScenicId, scenicId);
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
    public Activity selectByIdRequired(Long id) {
        Activity activity = activityMapper.selectById(id);
        if (activity == null) {
            log.error("该活动已被删除 [{}]", id);
            throw new BusinessException(ErrorCode.ACTIVITY_DELETE);
        }
        return activity;
    }

    @Override
    public void delete(ActivityDeleteRequest request) {
        LambdaUpdateWrapper<Activity> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(StrUtil.isNotBlank(request.getTitle()), Activity::getTitle, request.getTitle());
        wrapper.eq(request.getId() != null, Activity::getId, request.getId());
        activityMapper.delete(wrapper);
    }

    @Override
    public List<ActivityBaseDTO> scenicActivityList(Long scenicId) {
        long endLimit = sysConfigApi.getLong(ConfigConstant.SCENIC_ACTIVITY_LIMIT, 7);
        LocalDate start = LocalDate.now();
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Activity::getScenicId, scenicId);
        wrapper.between(Activity::getNowDate, start, start.plusDays(endLimit));
        wrapper.last(" order by id desc ");

        List<Activity> activityList = activityMapper.selectList(wrapper);
        if (CollUtil.isEmpty(activityList)) {
            return Lists.newArrayListWithCapacity(1);
        }
        return activityList.stream()
                .map(activityCalendar -> DataUtil.copy(activityCalendar, ActivityBaseDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * 过滤某一天的活动并进行对象映射
     * @param selectList 活动列表
     * @param date 天 yyyy-MM-dd
     * @return 该天的活动 倒叙列表
     */
    private List<ActivityBaseDTO> filterDateActivity(List<Activity> selectList, LocalDate date) {
        return selectList.stream().filter(activityCalendar -> activityCalendar.getNowDate().isEqual(date))
                .map(activityCalendar -> DataUtil.copy(activityCalendar, ActivityBaseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 活动标题重复校验 (在开始和截止时间内的活动名称不能重复)
     * @param title 标题名称
     * @param startDate 开始时间
     * @param endDate 截止时间
     */
    public void redoTitle(String title, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Activity::getTitle, title);
        wrapper.between(Activity::getNowDate, startDate, endDate);
        Integer count = activityMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("活动配置名称重复 [{}] [{}] [{}]", title, startDate, endDate);
            throw new BusinessException(ErrorCode.ACTIVITY_TITLE_REDO);
        }
    }

    /**
     * 活动标题重复校验
     * @param title 标题名称
     * @param id id 编辑时不能为空
     * @param localDate 日期 同一日期标题不能重复
     */
    public void redoTitle(String title, Long id, LocalDate localDate) {
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Activity::getTitle, title);
        wrapper.ne(id != null, Activity::getId, id);
        wrapper.eq(Activity::getNowDate, localDate);
        Integer count = activityMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("活动名称重复 [{}] [{}] [{}]", title, id, localDate);
            throw new BusinessException(ErrorCode.ACTIVITY_TITLE_REDO);
        }
    }
}
