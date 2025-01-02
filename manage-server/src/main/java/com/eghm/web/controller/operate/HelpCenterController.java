package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.help.HelpAddRequest;
import com.eghm.dto.operate.help.HelpEditRequest;
import com.eghm.dto.operate.help.HelpQueryRequest;
import com.eghm.model.HelpCenter;
import com.eghm.service.operate.HelpCenterService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.help.HelpDetailResponse;
import com.eghm.vo.operate.help.HelpResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@RestController
@Tag(name= "帮助中心")
@AllArgsConstructor
@RequestMapping(value = "/manage/help", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<HelpResponse>> listPage(@ParameterObject @Validated HelpQueryRequest request) {
        Page<HelpResponse> byPage = helpCenterService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody HelpAddRequest request) {
        helpCenterService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody HelpEditRequest request) {
        helpCenterService.update(request);
        return RespBody.success();
    }

    @GetMapping(value = "/select")
    @Operation(summary = "详情")
    public RespBody<HelpDetailResponse> select(@ParameterObject @Validated IdDTO dto) {
        HelpCenter center = helpCenterService.selectById(dto.getId());
        HelpDetailResponse response = DataUtil.copy(center, HelpDetailResponse.class);
        return RespBody.success(response);
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        helpCenterService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        helpCenterService.delete(request.getId());
        return RespBody.success();
    }
}
