package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.bulletin.TopBulletinVO;
import com.eghm.service.common.SysBulletinService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@RestController
@Api(tags = "系统公告")
public class BulletinController {

    private SysBulletinService sysBulletinService;

    @Autowired
    @SkipLogger
    public void setSysBulletinService(SysBulletinService sysBulletinService) {
        this.sysBulletinService = sysBulletinService;
    }

    /**
     * 获取首页公告列表
     */
    @GetMapping("/bulletin/list")
    @ApiOperation("获取首页公告列表")
    public RespBody<List<TopBulletinVO>> list() {
        List<TopBulletinVO> list = sysBulletinService.getList();
        return RespBody.success(list);
    }

}
