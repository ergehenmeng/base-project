package com.fanyin.controller.system;

import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.dto.system.log.OperationQueryRequest;
import com.fanyin.ext.Paging;
import com.fanyin.model.system.SystemOperationLog;
import com.fanyin.service.system.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/1/16 10:37
 */
@Controller
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 操作日期查询请求
     * @param request 查询条件
     * @return 分页
     */
    @PostMapping("/system/operation_log/list_page")
    @ResponseBody
    public Paging<SystemOperationLog> listPage(OperationQueryRequest request){
        return new Paging<>(operationLogService.getByPage(request));
    }

    /**
     * 响应结果信息
     * @param id di
     * @return 结果页面
     */
    @PostMapping("/public/system/operation_log/query_page")
    @Mark(RequestType.PAGE)
    public String queryPage(Model model, Integer id){
        String response = operationLogService.getResponseById(id);
        model.addAttribute("response",response);
        return "query_page";
    }
}
