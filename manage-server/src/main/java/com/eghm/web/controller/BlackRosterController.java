package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.roster.BlackRosterAddRequest;
import com.eghm.dto.roster.BlackRosterQueryRequest;
import com.eghm.model.BlackRoster;
import com.eghm.service.sys.BlackRosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@Api(tags = "访问黑名单")
@AllArgsConstructor
@RequestMapping("/manage/black/roster")
public class BlackRosterController {

    private final BlackRosterService blackRosterService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<BlackRoster>> listPage(BlackRosterQueryRequest request) {
        Page<BlackRoster> listByPage = blackRosterService.getByPage(request);
        return RespBody.success(PageData.toPage(listByPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@RequestBody @Validated BlackRosterAddRequest request) {
        blackRosterService.addBlackRoster(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        blackRosterService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/reload")
    @ApiOperation("刷新黑名单")
    public RespBody<Void> reload() {
        blackRosterService.reloadBlackRoster();
        return RespBody.success();
    }
}
