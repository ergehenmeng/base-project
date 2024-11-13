package com.eghm.web.controller.sys;

import com.eghm.dto.IdDTO;
import com.eghm.dto.sys.dept.DeptAddRequest;
import com.eghm.dto.sys.dept.DeptEditRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.sys.SysDeptService;
import com.eghm.vo.sys.ext.SysDeptResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/1/17 16:27
 */
@RestController
@Api(tags = "部门管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/dept", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeptController {

    private final SysDeptService sysDeptService;

    @GetMapping("/list")
    @ApiOperation("部门列表(不分页)")
    public RespBody<List<SysDeptResponse>> list(PagingQuery query) {
        List<SysDeptResponse> list = sysDeptService.getList(query);
        return RespBody.success(list);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody DeptAddRequest request) {
        sysDeptService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody DeptEditRequest request) {
        sysDeptService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysDeptService.deleteById(request.getId());
        return RespBody.success();
    }

}
