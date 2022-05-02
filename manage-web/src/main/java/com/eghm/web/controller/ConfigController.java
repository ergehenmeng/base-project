package com.eghm.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.sys.SysConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 二哥很猛
 * @date 2018/1/12 17:40
 */
@RestController
@Api(tags = "系统参数管理")
@AllArgsConstructor
public class ConfigController {

    private final SysConfigService sysConfigService;

    private final ProxyService proxyService;

    @PostMapping("/config/edit")
    @Mark
    @ApiOperation("编辑系统参数")
    public RespBody<Object> edit(ConfigEditRequest request) {
        sysConfigService.updateConfig(request);
        return RespBody.success();
    }

    /**
     * 参数编辑页面
     *
     * @param model 存放参数对象
     * @param id    主键
     * @return 页面
     */
    @GetMapping("/config/edit_page")
    public String editPage(Model model, Long id) {
        SysConfig config = sysConfigService.getById(id);
        model.addAttribute("config", config);
        return "config/edit_page";
    }

    /**
     * 分页获取系统参数配置
     *
     * @param request 查询
     * @return 分页列表
     */
    @GetMapping("config/list_page")
    @ApiOperation("系统参数列表")
    public Paging<SysConfig> listPage(ConfigQueryRequest request) {
        Page<SysConfig> listByPage = sysConfigService.getByPage(request);
        ProxyService finalProxy = this.proxyService;
        return DataUtil.convert(listByPage, sysConfig -> {
            finalProxy.getDictValue(DictConstant.CONFIG_CLASSIFY, sysConfig.getClassify());
            return sysConfig;
        });
    }

}
