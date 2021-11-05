package com.eghm.web.controller;

import com.eghm.dao.model.SysOperationLog;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.log.OperationQueryRequest;
import com.eghm.service.sys.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/1/16 10:37
 */
@RestController
@Api(tags = "操作日志管理")
public class OperationLogController {

    private OperationLogService operationLogService;

    @Autowired
    public void setOperationLogService(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * 操作日期查询请求
     *
     * @param request 查询条件
     * @return 分页
     */
    @GetMapping("/operation_log/list_page")
    @ApiOperation("日志列表")
    public Paging<SysOperationLog> listPage(OperationQueryRequest request) {
        return new Paging<>(operationLogService.getByPage(request));
    }

    /**
     * 响应结果信息
     *
     * @param id di
     * @return 结果页面
     */
    @GetMapping("/operation_log/{id}")
    @ApiImplicitParam(name = "id", value = "id主键", required = true)
    public String queryPage(@PathVariable("id") Long id) {
        return operationLogService.getResponseById(id);
    }
}
