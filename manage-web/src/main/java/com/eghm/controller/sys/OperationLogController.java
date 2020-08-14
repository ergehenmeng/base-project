package com.eghm.controller.sys;

import com.eghm.annotation.Mark;
import com.eghm.dao.model.sys.SysOperationLog;
import com.eghm.model.dto.sys.log.OperationQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.service.sys.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/system/operation_log/list_page")
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
    @GetMapping("/system/operation_log/query_page")
    @Mark
    public String queryPage(Model model, Integer id) {
        String response = operationLogService.getResponseById(id);
        model.addAttribute("response", response);
        return "query_page";
    }
}
