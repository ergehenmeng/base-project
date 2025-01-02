package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.roster.BlackRosterAddRequest;
import com.eghm.model.BlackRoster;
import com.eghm.service.sys.BlackRosterService;
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
@Tag(name= "访问黑名单")
@AllArgsConstructor
@RequestMapping(value = "/manage/black/roster", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlackRosterController {

    private final BlackRosterService blackRosterService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<BlackRoster>> listPage(@ParameterObject PagingQuery request) {
        Page<BlackRoster> listByPage = blackRosterService.getByPage(request);
        return RespBody.success(PageData.toPage(listByPage));
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
