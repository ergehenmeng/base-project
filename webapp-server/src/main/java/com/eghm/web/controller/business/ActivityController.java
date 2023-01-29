package com.eghm.web.controller.business;

import com.eghm.model.Activity;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.vo.business.activity.ActivityBaseResponse;
import com.eghm.model.vo.business.activity.ActivityVO;
import com.eghm.service.business.ActivityService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.SkipAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */
@RestController
@Api(tags = "活动")
@AllArgsConstructor
@RequestMapping("/webapp/activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/month")
    @ApiImplicitParam(name = "month", value = "月份yyyy-MM", required = true)
    @ApiOperation("查询月活动")
    @SkipAccess
    public List<ActivityBaseResponse> month(@RequestParam("month") String month) {
        return activityService.getMonthActivity(month, null);
    }

    @GetMapping("/detail")
    @ApiOperation("活动详情")
    @SkipAccess
    public ActivityVO detail(@Validated IdDTO dto) {
        Activity activity = activityService.selectByIdRequired(dto.getId());
        return DataUtil.copy(activity, ActivityVO.class);
    }
}
