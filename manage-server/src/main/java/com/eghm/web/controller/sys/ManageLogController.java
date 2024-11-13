package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.log.ManageQueryRequest;
import com.eghm.service.sys.ManageLogService;
import com.eghm.vo.operate.log.ManageLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2019/1/16 10:37
 */
@RestController
@Api(tags = "操作日志管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageLogController {

    private final ManageLogService manageLogService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<ManageLogResponse>> listPage(ManageQueryRequest request) {
        Page<ManageLogResponse> byPage = manageLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

}
