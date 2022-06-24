package com.eghm.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.sys.SysConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    private final ProxyService proxyService;

    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑系统参数")
    public RespBody<Void> edit(ConfigEditRequest request) {
        sysConfigService.updateConfig(request);
        return RespBody.success();
    }

    /**
     * 分页获取系统参数配置
     *
     * @param request 查询
     * @return 分页列表
     */
    @GetMapping("/listPage")
    @ApiOperation("系统参数列表")
    public RespBody<PageData<Object>> listPage(ConfigQueryRequest request) {
        Page<SysConfig> listByPage = sysConfigService.getByPage(request);
        ProxyService finalProxy = this.proxyService;
        return RespBody.success(DataUtil.convert(listByPage, sysConfig -> {
            finalProxy.getDictValue(DictConstant.CONFIG_CLASSIFY, sysConfig.getClassify());
            return sysConfig;
        }));
    }

}
