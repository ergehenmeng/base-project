package com.eghm.interfaces.manage.controller.sys;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.application.system.service.SmsLogApplicationService;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@AllArgsConstructor
@Tag(name = "短信日志管理")
@RequestMapping(value = "/manage/sms/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class SmsLogController {

    private final SmsLogApplicationService smsLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SmsLogResponse>> listPage(@ParameterObject SmsLogQueryRequest request) {
        Page<SmsLogResponse> byPage = smsLogService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

}
