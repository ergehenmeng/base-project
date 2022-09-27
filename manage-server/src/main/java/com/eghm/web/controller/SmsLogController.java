package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SmsLog;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.sms.SmsLogQueryRequest;
import com.eghm.service.sys.SmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信记录
 *
 * @author 二哥很猛
 * @date 2019/8/21 16:12
 */
@RestController
@Api(tags = "短信日志管理")
@AllArgsConstructor
@RequestMapping("/manage/sms/log")
public class SmsLogController {

    private final SmsLogService smsLogService;

    @GetMapping("/listPage")
    @ApiOperation("短信记录列表(分页)")
    public PageData<SmsLog> listPage(SmsLogQueryRequest request) {
        Page<SmsLog> byPage = smsLogService.getByPage(request);
        return PageData.toPage(byPage);
    }

}
