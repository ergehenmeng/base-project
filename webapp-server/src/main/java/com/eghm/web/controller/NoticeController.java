package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.operate.SysNoticeService;
import com.eghm.vo.operate.notice.NoticeDetailVO;
import com.eghm.vo.operate.notice.NoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@RestController
@Api(tags = "系统公告")
@AllArgsConstructor
@RequestMapping(value = "/webapp/notice", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {

    private final SysNoticeService sysNoticeService;

    @GetMapping("/limit")
    @ApiOperation("首页公告Top-N")
    public RespBody<List<NoticeVO>> list() {
        List<NoticeVO> list = sysNoticeService.getList();
        return RespBody.success(list);
    }

    @GetMapping("/listPage")
    @ApiOperation("公告列表")
    public RespBody<List<NoticeVO>> listPage(PagingQuery query) {
        List<NoticeVO> list = sysNoticeService.getList(query);
        return RespBody.success(list);
    }

    @GetMapping("/detail")
    @ApiOperation("公告详细信息")
    public RespBody<NoticeDetailVO> detail(@Validated IdDTO dto) {
        NoticeDetailVO detailed = sysNoticeService.detailById(dto.getId());
        return RespBody.success(detailed);
    }

}
