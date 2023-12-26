package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.activity.ActivityQueryRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Activity;
import com.eghm.service.business.ActivityService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.activity.ActivityResponse;
import com.eghm.vo.business.activity.ActivityVO;
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
    public RespBody<List<ActivityResponse>> month(@RequestParam("month") String month) {
        ActivityQueryRequest request = new ActivityQueryRequest();
        request.setMonth(month);
        List<ActivityResponse> monthActivity = activityService.getMonthActivity(request);
        return RespBody.success(monthActivity);
    }

    @GetMapping("/detail")
    @ApiOperation("活动详情")
    public RespBody<ActivityVO> detail(@Validated IdDTO dto) {
        Activity activity = activityService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(activity, ActivityVO.class));
    }
}
