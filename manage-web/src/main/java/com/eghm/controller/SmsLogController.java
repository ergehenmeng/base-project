package com.eghm.controller;

import com.eghm.dao.model.SmsLog;
import com.eghm.model.dto.sms.SmsLogQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.service.sys.SmsLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信记录
 *
 * @author 二哥很猛
 * @date 2019/8/21 16:12
 */
@Controller
public class SmsLogController {

    private SmsLogService smsLogService;

    @Autowired
    public void setSmsLogService(SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    /**
     * 分页查询短信记录列表
     */
    @PostMapping("/business/sms_log/list_page")
    @ResponseBody
    public Paging<SmsLog> listPage(SmsLogQueryRequest request) {
        PageInfo<SmsLog> byPage = smsLogService.getByPage(request);
        return new Paging<>(byPage);
    }

}
