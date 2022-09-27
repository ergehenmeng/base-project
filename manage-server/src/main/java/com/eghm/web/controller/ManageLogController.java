package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.ManageLog;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.log.ManageQueryRequest;
import com.eghm.service.sys.ManageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/1/16 10:37
 */
@RestController
@Api(tags = "操作日志管理")
@AllArgsConstructor
@RequestMapping("/manage/log")
public class ManageLogController {

    private final ManageLogService manageLogService;

    @GetMapping("/listPage")
    @ApiOperation("日志列表")
    public PageData<ManageLog> listPage(ManageQueryRequest request) {
        Page<ManageLog> byPage = manageLogService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @GetMapping("/select")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public String select(@RequestParam("id") Long id) {
        return manageLogService.getResponseById(id);
    }
}
