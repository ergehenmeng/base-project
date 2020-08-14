package com.eghm.controller.sys;

import com.eghm.annotation.Mark;
import com.eghm.dao.model.sys.SysDept;
import com.eghm.model.dto.sys.department.DepartmentAddRequest;
import com.eghm.model.dto.sys.department.DepartmentEditRequest;
import com.eghm.model.ext.RespBody;
import com.eghm.service.sys.SysDeptService;
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
    @PostMapping("/system/department/list_page")
    @ResponseBody
    public List<SysDept> listPage() {
        return sysDeptService.getDepartment();
    }


    /**
     * 添加部门页面
     */
    @GetMapping("/system/department/add_page")
    public String addPage(Model model, String code) {
        model.addAttribute("code", code);
        return "system/department/add_page";
    }

    /**
     * 添加部门节点信息
     */
    @PostMapping("/system/department/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(DepartmentAddRequest request) {
        sysDeptService.addDepartment(request);
        return RespBody.success();
    }

    /**
     * 编辑部门页面
     */
    @GetMapping("/system/department/edit_page")
    public String editPage(Model model, Integer id) {
        SysDept department = sysDeptService.getById(id);
        model.addAttribute("department", department);
        return "system/department/edit_page";
    }

    /**
     * 编辑部门节点信息
     */
    @PostMapping("/system/department/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(DepartmentEditRequest request) {
        sysDeptService.editDepartment(request);
        return RespBody.success();
    }

}
