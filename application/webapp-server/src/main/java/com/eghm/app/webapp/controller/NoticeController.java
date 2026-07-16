package com.eghm.app.webapp.controller;

import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.business.operation.delivery.service.SysNoticeService;
import com.eghm.business.operation.delivery.vo.NoticeDetailVO;
import com.eghm.business.operation.delivery.vo.NoticeTopVO;
import com.eghm.business.operation.delivery.vo.NoticeVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "系统公告")
@AllArgsConstructor
@RequestMapping(value = "/webapp/notice", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {

    private final SysNoticeService sysNoticeService;

    @GetMapping("/top")
    @Operation(summary = "首页公告Top-N")
    public RespBody<List<NoticeTopVO>> top() {
        List<NoticeTopVO> list = sysNoticeService.getTop();
        return RespBody.success(list);
    }

    @GetMapping("/listPage")
    @Operation(summary = "公告列表")
    public RespBody<List<NoticeVO>> listPage(@ParameterObject PagingQuery query) {
        List<NoticeVO> list = sysNoticeService.getList(query);
        return RespBody.success(list);
    }

    @GetMapping("/detail")
    @Operation(summary = "公告详细信息")
    public RespBody<NoticeDetailVO> detail(@ParameterObject @Validated IdDTO dto) {
        NoticeDetailVO detailed = sysNoticeService.detailById(dto.getId());
        return RespBody.success(detailed);
    }

}
