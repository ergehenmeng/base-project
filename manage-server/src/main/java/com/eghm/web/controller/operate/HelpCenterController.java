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
import com.eghm.vo.help.HelpDetailResponse;
import com.eghm.vo.help.HelpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
@AllArgsConstructor
@RequestMapping(value = "/manage/help", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<HelpResponse>> listPage(@Validated HelpQueryRequest request) {
        Page<HelpResponse> byPage = helpCenterService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody HelpAddRequest request) {
        helpCenterService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody HelpEditRequest request) {
        helpCenterService.update(request);
        return RespBody.success();
    }

    @GetMapping(value = "/select")
    @ApiOperation("详情")
    public RespBody<HelpDetailResponse> select(@Validated IdDTO dto) {
        HelpCenter center = helpCenterService.selectById(dto.getId());
        HelpDetailResponse response = DataUtil.copy(center, HelpDetailResponse.class);
        return RespBody.success(response);
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        helpCenterService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        helpCenterService.delete(request.getId());
        return RespBody.success();
    }
}
