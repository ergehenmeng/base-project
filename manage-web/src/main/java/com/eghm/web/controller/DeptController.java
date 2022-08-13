package com.eghm.web.controller;

import com.eghm.dao.model.SysDept;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.model.dto.dept.DeptEditRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/1/17 16:27
 */
@RestController
@Api(tags = "部门管理")
@AllArgsConstructor
@RequestMapping("/manage/dept")
public class DeptController {

    private final SysDeptService sysDeptService;

    @GetMapping("/listPage")
    @ApiOperation("部门列表(不分页)")
    public List<SysDept> listPage() {
        return sysDeptService.getDepartment();
    }

    @PostMapping("/create")
    @ApiOperation("添加部门")
    public RespBody<Void> create(@Validated @RequestBody DeptAddRequest request) {
        sysDeptService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑部门")
    public RespBody<Void> update(@Validated @RequestBody DeptEditRequest request) {
        sysDeptService.update(request);
        return RespBody.success();
    }

}
