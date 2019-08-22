package com.fanyin.controller.business;

import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionAddRequest;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.AppVersionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:08
 */
@RestController
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    /**
     * app版本管理列表
     */
    @RequestMapping("/business/version/list_page")
    public Paging<AppVersion> listPage(VersionQueryRequest request){
        PageInfo<AppVersion> byPage = appVersionService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 添加app版本信息
     */
    @RequestMapping("/business/version/add")
    public RespBody add(VersionAddRequest request){
        return RespBody.getInstance();
    }
}
