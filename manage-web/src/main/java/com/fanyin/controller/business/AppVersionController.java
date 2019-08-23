package com.fanyin.controller.business;

import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionAddRequest;
import com.fanyin.model.dto.business.version.VersionEditRequest;
import com.fanyin.model.dto.business.version.VersionQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.AppVersionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:08
 */
@Controller
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    /**
     * app版本管理列表
     */
    @RequestMapping("/business/version/list_page")
    @ResponseBody
    public Paging<AppVersion> listPage(VersionQueryRequest request){
        PageInfo<AppVersion> byPage = appVersionService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 添加app版本信息
     */
    @RequestMapping("/business/version/add")
    @ResponseBody
    public RespBody add(VersionAddRequest request){
        appVersionService.addAppVersion(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑保存app版本信息
     */
    @RequestMapping("/business/version/edit")
    @ResponseBody
    public RespBody edit(VersionEditRequest request){
        appVersionService.editAppVersion(request);
        return RespBody.getInstance();
    }

    /**
     * 上架app版本
     * @param id  主键
     */
    @RequestMapping("/business/version/put_away")
    @ResponseBody
    public RespBody putAway(Integer id){
        appVersionService.putAwayVersion(id);
        return RespBody.getInstance();
    }

    /**
     * 下架app版本
     * @param id 主键
     */
    @RequestMapping("/business/version/sold_out")
    @ResponseBody
    public RespBody soleOut(Integer id){
        appVersionService.soleOutVersion(id);
        return RespBody.getInstance();
    }

    /**
     * 删除版本信息
     */
    @RequestMapping("/business/version/delete")
    @ResponseBody
    public RespBody delete(Integer id){
        appVersionService.deleteVersion(id);
        return RespBody.getInstance();
    }
}
