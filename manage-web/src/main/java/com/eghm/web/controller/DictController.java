package com.eghm.web.controller;

import com.eghm.dao.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysDictService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:10
 */
@RestController
@Api(tags = "数据字典管理")
@AllArgsConstructor
@RequestMapping("/dict")
public class DictController {

    private final SysDictService sysDictService;

    /**
     * 分页查询数据字典列表
     *
     * @param request 前台参数
     * @return 分页列表
     */
    @GetMapping("/listPage")
    @ApiOperation("数据字典列表(分页)")
    public RespBody<PageData<SysDict>> listPage(DictQueryRequest request) {
        return RespBody.success(PageData.toPage(sysDictService.getByPage(request)));
    }

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     * @return 成功响应
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("数据字典列表(分页)")
    public RespBody<Void> add(@Valid DictAddRequest request) {
        sysDictService.addDict(request);
        return RespBody.success();
    }

    /**
     * 编辑数据字典
     *
     * @param request 前台参数
     * @return 结果
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑数据字典")
    public RespBody<Void> edit(@Valid DictEditRequest request) {
        sysDictService.updateDict(request);
        return RespBody.success();
    }


    /**
     * 删除数据字典项
     *
     * @param id 主键
     * @return 成功响应
     */
    @PostMapping("/delete")
    @Mark
    @ApiOperation("删除数据字典")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> delete(@RequestParam("id") Long id) {
        sysDictService.deleteDict(id);
        return RespBody.success();
    }

}
