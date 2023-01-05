package com.eghm.web.controller.business;

import com.eghm.model.vo.business.activity.ActivityBaseDTO;
import com.eghm.service.business.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/webapp/scenic")
public class ScenicController {

    private final ActivityService activityService;

    @GetMapping("/activity")
    @ApiOperation("景区活动列表")
    @ApiImplicitParam(name = "scenicId", value = "景区id", required = true)
    public List<ActivityBaseDTO> activityList(@RequestParam("scenicId") Long scenicId) {
        return activityService.scenicActivityList(scenicId);
    }
}
