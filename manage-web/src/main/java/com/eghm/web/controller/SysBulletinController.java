package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.SysBulletin;
import com.eghm.model.dto.bulletin.BulletinAddRequest;
import com.eghm.model.dto.bulletin.BulletinEditRequest;
import com.eghm.model.dto.bulletin.BulletinHandleRequest;
import com.eghm.model.dto.bulletin.BulletinQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.common.SysBulletinService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/8/23 13:35
 */
@RestController
@Api(tags = "公告管理")
@AllArgsConstructor
public class SysBulletinController {

    private final SysBulletinService sysBulletinService;

    private final ProxyService proxyService;

    /**
     * 系统公告列表查询
     */
    @ApiOperation("公告列表(分页)")
    @GetMapping("/bulletin/list_page")
    public Paging<SysBulletin> listPage(BulletinQueryRequest request) {
        Page<SysBulletin> byPage = sysBulletinService.getByPage(request);
        return DataUtil.convert(byPage, notice -> {
            notice.setClassifyName(proxyService.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()));
            return notice;
        });
    }

    /**
     * 新增公告信息
     */
    @PostMapping("/bulletin/add")
    @Mark
    @ApiOperation("新增公告信息")
    public RespBody<Object> add(@Valid BulletinAddRequest request) {
        sysBulletinService.addNotice(request);
        return RespBody.success();
    }

    /**
     * 公告编辑页面
     */
    @GetMapping("/bulletin/{id}")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @ApiOperation("公告信息查询")
    public SysBulletin editPage(@PathVariable("id") Long id) {
        return sysBulletinService.getById(id);
    }


    @PostMapping("/bulletin/handle")
    @Mark
    @ApiOperation("公告操作(发布,取消发布,删除)")
    public RespBody<Object> handle(@Valid BulletinHandleRequest request) {
        if (request.getState() == BulletinHandleRequest.PUBLISH) {
            sysBulletinService.publish(request.getId());
        } else if (request.getState() == BulletinHandleRequest.CANCEL_PUBLISH) {
            sysBulletinService.cancelPublish(request.getId());
        } else {
            sysBulletinService.deleteNotice(request.getId());
        }
        return RespBody.success();
    }

    /**
     * 编辑公告信息
     */
    @PostMapping("/bulletin/edit")
    @Mark
    @ApiOperation("编辑公告")
    public RespBody<Object> edit(@Valid BulletinEditRequest request) {
        sysBulletinService.editNotice(request);
        return RespBody.success();
    }


}
