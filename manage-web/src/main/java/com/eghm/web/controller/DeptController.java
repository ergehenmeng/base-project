package com.eghm.web.controller;

import com.eghm.dao.model.SysDept;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.model.dto.dept.DeptEditRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysDeptService;
import com.eghm.web.annotation.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/1/17 16:27
 */
@Controller
public class DeptController {

    private SysDeptService sysDeptService;

    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 查询所有部门列表
     *
     * @return list
     */
    @PostMapping("/dept/list_page")
    @ResponseBody
    public List<SysDept> listPage() {
        return sysDeptService.getDepartment();
    }


    /**
     * 添加部门页面
     */
    @GetMapping("/dept/add_page")
    public String addPage(Model model, String code) {
        model.addAttribute("code", code);
        return "dept/add_page";
    }

    /**
     * 添加部门节点信息
     */
    @PostMapping("/dept/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(DeptAddRequest request) {
        sysDeptService.addDepartment(request);
        return RespBody.success();
    }

    /**
     * 编辑部门页面
     */
    @GetMapping("/dept/edit_page")
    public String editPage(Model model, Integer id) {
        SysDept dept = sysDeptService.getById(id);
        model.addAttribute("dept", dept);
        return "dept/edit_page";
    }

    /**
     * 编辑部门节点信息
     */
    @PostMapping("/dept/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(DeptEditRequest request) {
        sysDeptService.editDepartment(request);
        return RespBody.success();
    }

}
