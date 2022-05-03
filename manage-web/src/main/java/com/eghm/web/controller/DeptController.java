package com.eghm.web.controller;

import com.eghm.dao.model.SysDept;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.model.dto.dept.DeptEditRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysDeptService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/1/17 16:27
 */
@RestController
@Api(tags = "部门管理")
@AllArgsConstructor
@RequestMapping("/dept")
public class DeptController {

    private final SysDeptService sysDeptService;

    /**
     * 查询所有部门列表
     *
     * @return list
     */
    @GetMapping("/listPage")
    @ApiOperation("部门列表(不分页)")
    public List<SysDept> listPage() {
        return sysDeptService.getDepartment();
    }

    /**
     * 添加部门节点信息
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("添加部门")
    public RespBody<Void> add(@Valid DeptAddRequest request) {
        sysDeptService.addDepartment(request);
        return RespBody.success();
    }

    /**
     * 编辑部门节点信息
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑部门")
    public RespBody<Void> edit(@Valid DeptEditRequest request) {
        sysDeptService.editDepartment(request);
        return RespBody.success();
    }

}
