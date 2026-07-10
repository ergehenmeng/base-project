package com.eghm.interfaces.manage.controller.sys;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.roster.BlackRosterAddRequest;
import com.eghm.application.system.query.BlackRosterQueryService;
import com.eghm.application.system.service.BlackRosterApplicationService;
import com.eghm.application.shared.vo.sys.roster.BlackRosterResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@RestController
@AllArgsConstructor
@Tag(name = "访问黑名单")
@RequestMapping(value = "/manage/black/roster", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlackRosterController {

    private final BlackRosterApplicationService blackRosterService;

    private final BlackRosterQueryService blackRosterQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<BlackRosterResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<BlackRosterResponse> listByPage = blackRosterQueryService.getByPage(request);
        return RespBody.success(PageData.convert(listByPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated BlackRosterAddRequest request) {
        blackRosterService.addBlackRoster(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        blackRosterService.deleteById(dto.getId());
        return RespBody.success();
    }

}
