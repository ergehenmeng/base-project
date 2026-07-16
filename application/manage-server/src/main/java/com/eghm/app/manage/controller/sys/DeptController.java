package com.eghm.app.manage.controller.sys;

import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.platform.iam.dto.DeptAddRequest;
import com.eghm.platform.iam.dto.DeptEditRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.iam.service.SysDeptService;
import com.eghm.platform.iam.vo.SysDeptResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/1/17 16:27
 */
@RestController
@AllArgsConstructor
@Tag(name = "部门管理")
@RequestMapping(value = "/manage/dept", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeptController {

    private final SysDeptService sysDeptService;

    @GetMapping("/list")
    @Operation(summary = "部门列表(不分页)")
    public RespBody<List<SysDeptResponse>> list(@ParameterObject PagingQuery query) {
        List<SysDeptResponse> list = sysDeptService.getList(query);
        return RespBody.success(list);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody DeptAddRequest request) {
        sysDeptService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody DeptEditRequest request) {
        sysDeptService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysDeptService.deleteById(request.getId());
        return RespBody.success();
    }

}
