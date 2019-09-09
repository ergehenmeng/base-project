package com.fanyin.controller.system;

import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.dao.model.system.SystemDepartment;
import com.fanyin.model.dto.system.department.DepartmentAddRequest;
import com.fanyin.model.dto.system.department.DepartmentEditRequest;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.system.SystemDepartmentService;
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
public class DepartmentController {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    /**
     * 查询所有部门列表
     * @return list
     */
    @PostMapping("/system/department/list_page")
    @Mark(RequestType.SELECT)
    @ResponseBody
    public List<SystemDepartment> listPage(){
        return systemDepartmentService.getDepartment();
    }


    /**
     * 添加部门页面
     */
    @GetMapping("/system/department/add_page")
    @Mark(RequestType.PAGE)
    public String addPage(Model model, String code){
        model.addAttribute("code",code);
        return "system/department/add_page";
    }

    /**
     * 添加部门节点信息
     */
    @PostMapping("/system/department/add")
    @Mark(RequestType.INSERT)
    @ResponseBody
    public RespBody add(DepartmentAddRequest request){
        systemDepartmentService.addDepartment(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑部门页面
     */
    @GetMapping("/system/department/edit_page")
    @Mark(RequestType.PAGE)
    public String editPage(Model model,Integer id){
        SystemDepartment department = systemDepartmentService.getById(id);
        model.addAttribute("department",department);
        return "system/department/edit_page";
    }

    /**
     * 编辑部门节点信息
     */
    @PostMapping("/system/department/edit")
    @Mark(RequestType.UPDATE)
    @ResponseBody
    public RespBody edit(DepartmentEditRequest request){
        systemDepartmentService.editDepartment(request);
        return RespBody.getInstance();
    }

}
