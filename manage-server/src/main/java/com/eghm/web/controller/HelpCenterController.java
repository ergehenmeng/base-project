package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
@AllArgsConstructor
@RequestMapping("/manage/help")
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/listPage")
    @ApiOperation("帮助列表")
    public RespBody<PageData<HelpCenter>> listPage(@Validated HelpQueryRequest request) {
        Page<HelpCenter> byPage = helpCenterService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody HelpAddRequest request) {
       helpCenterService.create(request);
        return RespBody.success();
    }

    @GetMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody HelpEditRequest request) {
        helpCenterService.update(request);
        return RespBody.success();
    }

    @GetMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        helpCenterService.delete(request.getId());
        return RespBody.success();
    }
}
