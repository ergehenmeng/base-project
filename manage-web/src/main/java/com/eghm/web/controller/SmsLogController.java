package com.eghm.web.controller;

import com.eghm.dao.model.SmsLog;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.sms.SmsLogQueryRequest;
import com.eghm.service.sys.SmsLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信记录
 *
 * @author 二哥很猛
 * @date 2019/8/21 16:12
 */
@RestController
@Api(tags = "短信日志管理")
public class SmsLogController {

    private SmsLogService smsLogService;

    @Autowired
    public void setSmsLogService(SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    /**
     * 分页查询短信记录列表
     */
    @GetMapping("/sms_log/list_page")
    @ApiOperation("短信记录列表(分页)")
    public Paging<SmsLog> listPage(SmsLogQueryRequest request) {
        PageInfo<SmsLog> byPage = smsLogService.getByPage(request);
        return new Paging<>(byPage);
    }

}
