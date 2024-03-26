package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.account.score.ScoreAccountQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ScoreAccountLogService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.account.ScoreAccountLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/16
 */

@RestController
@Api(tags = "商户积分")
@AllArgsConstructor
@RequestMapping(value = "/manage/score/account/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreAccountLogController {

    private final ScoreAccountLogService scoreAccountLogService;

    @GetMapping("/listPage")
    @ApiOperation("积分变动列表")
    public RespBody<PageData<ScoreAccountLogResponse>> listPage(ScoreAccountQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ScoreAccountLogResponse> byPage = scoreAccountLogService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response, ScoreAccountQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<ScoreAccountLogResponse> byPage = scoreAccountLogService.getList(request);
        EasyExcelUtil.export(response, "积分变动记录", byPage, ScoreAccountLogResponse.class);
    }
}
