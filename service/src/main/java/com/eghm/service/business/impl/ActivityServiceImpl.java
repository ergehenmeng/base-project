package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.activity.*;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ActivityMapper;
import com.eghm.mapper.ScenicMapper;
import com.eghm.model.Activity;
import com.eghm.model.Scenic;
import com.eghm.service.business.ActivityService;
import com.eghm.service.business.CommonService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.activity.ActivityBaseDTO;
import com.eghm.vo.business.activity.ActivityDetailResponse;
import com.eghm.vo.business.activity.ActivityResponse;
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
 * @since 2022/7/18
 */
@Service("activityService")
@AllArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    private final ScenicMapper scenicMapper;

    private final SysConfigApi sysConfigApi;

    private final CommonService commonService;

    private final ActivityMapper activityMapper;

    @Override
    public void createBatch(ActivityConfigRequest request) {
        this.redoTitle(request.getTitle(), request.getStartDate(), request.getEndDate());
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        commonService.checkMaxDay(ConfigConstant.ACTIVITY_CONFIG_MAX_DAY, between);
        for (int i = 0; i < between; i++) {
            LocalDate plusDays = request.getStartDate().plusDays(i);
            if (!request.getWeek().contains(plusDays.getDayOfWeek().getValue())) {
                continue;
            }
            Activity activity = DataUtil.copy(request, Activity.class);
            // 批量新增的活动默认不支持评论
            activity.setCommentSupport(false);
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
    public List<ActivityResponse> getMonthActivity(ActivityQueryRequest request) {
        LocalDate startDate = LocalDate.parse(request.getMonth() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = startDate.plusMonths(1);
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.isNull(request.getScenicId() == null, Activity::getScenicId);
        wrapper.eq(request.getScenicId() != null, Activity::getScenicId, request.getScenicId());
        wrapper.ge(Activity::getNowDate, startDate);
        wrapper.lt(Activity::getNowDate, endDate);
        wrapper.orderByDesc(Activity::getId);
        List<Activity> selectList = activityMapper.selectList(wrapper);
        int dayOfMonth = startDate.lengthOfMonth();
        List<ActivityResponse> responseList = Lists.newArrayListWithExpectedSize(31);
        for (int i = 0; i < dayOfMonth; i++) {
            ActivityResponse response = new ActivityResponse();
            response.setNowDate(startDate.plusDays(i));
            response.setActivityList(this.filterDateActivity(selectList, response.getNowDate()));
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public ActivityDetailResponse getByDetail(Long id) {
        Activity activity = this.selectByIdRequired(id);
        ActivityDetailResponse response = DataUtil.copy(activity, ActivityDetailResponse.class);
        if (activity.getScenicId() != null) {
            Scenic scenic = scenicMapper.selectById(activity.getScenicId());
            response.setScenicName(scenic.getScenicName());
        }
        return response;
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
        if (request.getId() == null && StrUtil.isBlank(request.getTitle())) {
            throw new BusinessException(ErrorCode.ACTIVITY_TITLE_NULL);
        }
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
        wrapper.orderByDesc(Activity::getId);
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
     *
     * @param selectList 活动列表
     * @param date       天 yyyy-MM-dd
     * @return 该天的活动 倒叙列表
     */
    private List<ActivityBaseDTO> filterDateActivity(List<Activity> selectList, LocalDate date) {
        return selectList.stream().filter(activityCalendar -> activityCalendar.getNowDate().isEqual(date))
                .map(activityCalendar -> DataUtil.copy(activityCalendar, ActivityBaseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 活动标题重复校验 (在开始和截止时间内的活动名称不能重复)
     *
     * @param title     标题名称
     * @param startDate 开始时间
     * @param endDate   截止时间
     */
    public void redoTitle(String title, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Activity::getTitle, title);
        wrapper.between(Activity::getNowDate, startDate, endDate);
        Long count = activityMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("活动配置名称重复 [{}] [{}] [{}]", title, startDate, endDate);
            throw new BusinessException(ErrorCode.ACTIVITY_TITLE_REDO);
        }
    }

    /**
     * 活动标题重复校验
     *
     * @param title     标题名称
     * @param id        id 编辑时不能为空
     * @param localDate 日期 同一日期标题不能重复
     */
    public void redoTitle(String title, Long id, LocalDate localDate) {
        LambdaQueryWrapper<Activity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Activity::getTitle, title);
        wrapper.ne(id != null, Activity::getId, id);
        wrapper.eq(Activity::getNowDate, localDate);
        Long count = activityMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("活动名称重复 [{}] [{}] [{}]", title, id, localDate);
            throw new BusinessException(ErrorCode.ACTIVITY_TITLE_REDO);
        }
    }
}
