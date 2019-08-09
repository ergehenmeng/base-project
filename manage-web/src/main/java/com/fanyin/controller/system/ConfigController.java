package com.fanyin.controller.system;


import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.constants.DictConstant;
import com.fanyin.controller.AbstractController;
import com.fanyin.dto.system.config.ConfigEditRequest;
import com.fanyin.dto.system.config.ConfigQueryRequest;
import com.fanyin.ext.Paging;
import com.fanyin.ext.RespBody;
import com.fanyin.model.system.SystemConfig;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.service.system.SystemConfigService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Mark(RequestType.UPDATE)
    public RespBody edit(ConfigEditRequest request){
        systemConfigService.updateConfig(request);
        return RespBody.getInstance();
    }

    /**
     * 参数编辑页面
     * @param model 存放参数对象
     * @param id 主键
     * @return 页面
     */
    @PostMapping("/public/system/config/edit_page")
    @Mark(RequestType.PAGE)
    public String editPage(Model model, Integer id){
        SystemConfig config = systemConfigService.getById(id);
        model.addAttribute("config",config);
        return "public/system/config/edit_page";
    }

    /**
     * 分页获取系统参数配置
     * @param request 查询
     * @return 分页列表
     */
    @PostMapping("/system/config/list_page")
    @ResponseBody
    @Mark(RequestType.SELECT)
    public Paging<SystemConfig> listPage(ConfigQueryRequest request){
        PageInfo<SystemConfig> listByPage = systemConfigService.getByPage(request);
        return DataUtil.transform(listByPage,systemConfig -> {
            String dictValue = cacheProxyService.getDictValue(DictConstant.CONFIG_CLASSIFY, systemConfig.getClassify());
            systemConfig.setClassifyName(dictValue);
            return systemConfig;
        });
    }


}
