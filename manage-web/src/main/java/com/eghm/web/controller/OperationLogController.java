package com.eghm.web.controller;

import com.eghm.dao.model.SysOperationLog;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.log.OperationQueryRequest;
import com.eghm.service.sys.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/1/16 10:37
 */
@Controller
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
    @ResponseBody
    public Paging<SysOperationLog> listPage(OperationQueryRequest request) {
        return new Paging<>(operationLogService.getByPage(request));
    }

    /**
     * 响应结果信息
     *
     * @param id di
     * @return 结果页面
     */
    @GetMapping("/operation_log/query_page")
    public String queryPage(Model model, Long id) {
        String response = operationLogService.getResponseById(id);
        model.addAttribute("response", response);
        return "query_page";
    }
}
