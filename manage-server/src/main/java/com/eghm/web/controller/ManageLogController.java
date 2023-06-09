package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.log.ManageQueryRequest;
import com.eghm.model.ManageLog;
import com.eghm.service.sys.ManageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @SkipPerm
    @ApiOperation("日志详细信息")
    public String select(@Validated IdDTO dto) {
        return manageLogService.getResponseById(dto.getId());
    }
}
