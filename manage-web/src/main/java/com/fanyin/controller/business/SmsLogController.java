package com.fanyin.controller.business;

import com.fanyin.controller.AbstractController;
import com.fanyin.dao.model.system.SmsLog;
import com.fanyin.model.dto.business.SmsLogQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.system.SmsLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信记录
 * @author 二哥很猛
 * @date 2019/8/21 16:12
 */
@RestController
public class SmsLogController extends AbstractController {

    @Autowired
    private SmsLogService smsLogService;

    /**
     * 分页查询短信记录列表
     */
    @RequestMapping("/sms_log/list_page")
    public Paging<SmsLog> listPage(SmsLogQueryRequest request){
        PageInfo<SmsLog> byPage = smsLogService.getByPage(request);
        return new Paging<>(byPage);
    }

}
