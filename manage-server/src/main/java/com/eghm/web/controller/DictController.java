package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.dict.*;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysDictItem;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.DictItemResponse;
import com.eghm.vo.sys.DictResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:10
 */
@RestController
@Api(tags = "数据字典管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/dict", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictController {

    private final SysDictService sysDictService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<DictResponse>> listPage(DictQueryRequest request) {
        Page<DictResponse> byPage = sysDictService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody DictAddRequest request) {
        sysDictService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody DictEditRequest request) {
        sysDictService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        sysDictService.delete(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/item/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("选项新增")
    public RespBody<Void> itemCreate(@Validated @RequestBody DictItemAddRequest request) {
        sysDictService.itemCreate(request);
        return RespBody.success();
    }

    @PostMapping(value = "/item/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("选项编辑")
    public RespBody<Void> itemUpdate(@Validated @RequestBody DictItemEditRequest request) {
        sysDictService.itemUpdate(request);
        return RespBody.success();
    }

    @PostMapping(value = "/item/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("选项删除")
    public RespBody<Void> itemDelete(@Validated @RequestBody IdDTO dto) {
        sysDictService.itemDelete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/itemList")
    @ApiOperation("查询数据字典")
    @SkipPerm
    @ApiImplicitParam(name = "nid", value = "字典编码", required = true, dataType = "String")
    public RespBody<List<DictItemResponse>> itemList(@RequestParam("nid") String nid) {
        List<SysDictItem> dictList = sysDictService.getDictByNid(nid);
        List<DictItemResponse> responseList = DataUtil.copy(dictList, DictItemResponse.class);
        return RespBody.success(responseList);
    }
}
