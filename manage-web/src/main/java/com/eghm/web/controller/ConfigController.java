package com.eghm.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysConfigService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
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
@RequestMapping("/config")
public class ConfigController {

    private final SysConfigService sysConfigService;

    private final SysDictService sysDictService;

    @PostMapping("/update")
    @ApiOperation("更新系统参数")
    public RespBody<Void> update(@Validated @RequestBody ConfigEditRequest request) {
        sysConfigService.update(request);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("系统参数列表")
    public PageData<SysConfig> listPage(ConfigQueryRequest request) {
        Page<SysConfig> listByPage = sysConfigService.getByPage(request);
        SysDictService finalProxy = this.sysDictService;
        return DataUtil.convert(listByPage, sysConfig -> {
            finalProxy.getDictValue(DictConstant.CONFIG_CLASSIFY, sysConfig.getClassify());
            return sysConfig;
        });
    }

}
