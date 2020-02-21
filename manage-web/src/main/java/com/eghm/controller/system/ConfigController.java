package com.eghm.controller.system;


import com.eghm.annotation.Mark;
import com.eghm.constants.DictConstant;
import com.eghm.controller.AbstractController;
import com.eghm.dao.model.system.SystemConfig;
import com.eghm.model.dto.system.config.ConfigEditRequest;
import com.eghm.model.dto.system.config.ConfigQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.system.SystemConfigService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author 二哥很猛
 * @date 2018/1/12 17:40
 */
@Controller
public class ConfigController extends AbstractController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CacheProxyService cacheProxyService;

    @PostMapping("/system/config/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(ConfigEditRequest request) {
        systemConfigService.updateConfig(request);
        return RespBody.getInstance();
    }

    /**
     * 参数编辑页面
     *
     * @param model 存放参数对象
     * @param id    主键
     * @return 页面
     */
    @GetMapping("/system/config/edit_page")
    public String editPage(Model model, Integer id) {
        SystemConfig config = systemConfigService.getById(id);
        model.addAttribute("config", config);
        return "system/config/edit_page";
    }

    /**
     * 分页获取系统参数配置
     *
     * @param request 查询
     * @return 分页列表
     */
    @PostMapping("/system/config/list_page")
    @ResponseBody
    public Paging<SystemConfig> listPage(ConfigQueryRequest request) {
        PageInfo<SystemConfig> listByPage = systemConfigService.getByPage(request);
        return DataUtil.transform(listByPage, systemConfig -> {
            String dictValue = cacheProxyService.getDictValue(DictConstant.CONFIG_CLASSIFY, systemConfig.getClassify());
            systemConfig.setClassifyName(dictValue);
            return systemConfig;
        });
    }


}
