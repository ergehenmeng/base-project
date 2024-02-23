package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sms.SmsLogQueryRequest;
import com.eghm.model.SmsLog;
import com.eghm.service.sys.SmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信记录
 *
 * @author 二哥很猛
 * @since 2019/8/21 16:12
 */
@RestController
@Api(tags = "短信日志管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/sms/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class SmsLogController {

    private final SmsLogService smsLogService;

    @GetMapping("/listPage")
    @ApiOperation("短信记录列表(分页)")
    public RespBody<PageData<SmsLog>> listPage(SmsLogQueryRequest request) {
        Page<SmsLog> byPage = smsLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

}
