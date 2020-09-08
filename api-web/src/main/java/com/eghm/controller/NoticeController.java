package com.eghm.controller;

import com.eghm.annotation.SkipLogger;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.eghm.service.common.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@RestController
@Api(tags = "公告")
public class NoticeController {

    private SysNoticeService sysNoticeService;

    @Autowired
    @SkipLogger
    public void setSysNoticeService(SysNoticeService sysNoticeService) {
        this.sysNoticeService = sysNoticeService;
    }

    /**
     * 获取首页公告列表
     */
    @PostMapping("/notice/list")
    @ApiOperation("获取首页公告列表")
    public RespBody<List<TopNoticeVO>> list() {
        List<TopNoticeVO> list = sysNoticeService.getList();
        return RespBody.success(list);
    }


}
