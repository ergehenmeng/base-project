package com.eghm.web.controller.business;

import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.business.statistics.CollectRequest;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.business.MemberService;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.statistics.MemberStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@RestController
@Api(tags = "统计分析")
@AllArgsConstructor
@RequestMapping(value = "/manage/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    private final MemberService memberService;

    private final MemberCollectService memberCollectService;

    @GetMapping("/sexChannel")
    @ApiOperation("注册渠道统计")
    public RespBody<MemberStatisticsVO> sexChannel(DateRequest request) {
        MemberStatisticsVO statistics = memberService.sexChannel(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/dayRegister")
    @ApiOperation("注册统计(按天)")
    public RespBody<List<MemberRegisterVO>> dayRegister(DateRequest request) {
        this.setNull(request);
        List<MemberRegisterVO> statistics = memberService.dayRegister(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/collect")
    @ApiOperation("收藏量")
    public RespBody<List<CollectStatisticsVO>> collect(CollectRequest request) {
        List<CollectStatisticsVO> statistics = memberCollectService.dayCollect(request);
        return RespBody.success(statistics);
    }

    /**
     * 设置默认值
     *
     * @param request request
     */
    private void setNull(DateRequest request) {
        if (request.getStartDate() == null && request.getEndDate() == null) {
            LocalDate endDate = LocalDate.now();
            request.setEndDate(endDate);
            request.setStartDate(endDate.minusMonths(1));
        }
    }

}
