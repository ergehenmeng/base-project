package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.config.ConfigEditRequest;
import com.eghm.dto.config.ConfigQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysConfig;
import com.eghm.service.sys.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 二哥很猛
 * @date 2018/1/12 17:40
 */
@RestController
@Api(tags = "系统参数管理")
@AllArgsConstructor
@RequestMapping("/manage/config")
public class ConfigController {

    private final SysConfigService sysConfigService;

    @GetMapping("/listPage")
    @ApiOperation("系统参数列表")
    public RespBody<PageData<SysConfig>> listPage(ConfigQueryRequest request) {
        Page<SysConfig> listByPage = sysConfigService.getByPage(request);
        return RespBody.success(PageData.toPage(listByPage));
    }

    @PostMapping("/update")
    @ApiOperation("更新系统参数")
    public RespBody<Void> update(@Validated @RequestBody ConfigEditRequest request) {
        sysConfigService.update(request);
        return RespBody.success();
    }

}
