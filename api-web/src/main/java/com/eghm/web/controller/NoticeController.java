package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.eghm.service.common.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@RestController
@Api(tags = "系统公告")
@AllArgsConstructor
@RequestMapping("/webapp/notice")
public class NoticeController {

    private final SysNoticeService sysNoticeService;

    /**
     * 获取首页公告列表
     */
    @GetMapping("/list")
    @ApiOperation("获取首页公告列表")
    public RespBody<List<TopNoticeVO>> list() {
        List<TopNoticeVO> list = sysNoticeService.getList();
        return RespBody.success(list);
    }

}
