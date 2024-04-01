package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.help.HelpAddRequest;
import com.eghm.dto.help.HelpEditRequest;
import com.eghm.dto.help.HelpQueryRequest;
import com.eghm.model.HelpCenter;
import com.eghm.service.common.HelpCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
@AllArgsConstructor
@RequestMapping(value = "/manage/help", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<HelpCenter>> listPage(@Validated HelpQueryRequest request) {
        Page<HelpCenter> byPage = helpCenterService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody HelpAddRequest request) {
        helpCenterService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody HelpEditRequest request) {
        helpCenterService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        helpCenterService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        helpCenterService.delete(request.getId());
        return RespBody.success();
    }
}
