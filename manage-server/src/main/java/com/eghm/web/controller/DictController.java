package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysDict;
import com.eghm.dto.IdDTO;
import com.eghm.dto.dict.DictAddRequest;
import com.eghm.dto.dict.DictEditRequest;
import com.eghm.dto.dict.DictQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.sys.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:10
 */
@RestController
@Api(tags = "数据字典管理")
@AllArgsConstructor
@RequestMapping("/manage/dict")
public class DictController {

    private final SysDictService sysDictService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<SysDict>> listPage(DictQueryRequest request) {
        Page<SysDict> byPage = sysDictService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody DictAddRequest request) {
        sysDictService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody DictEditRequest request) {
        sysDictService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysDictService.delete(dto.getId());
        return RespBody.success();
    }

}
