package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.line.LineAddRequest;
import com.eghm.dto.business.line.LineEditRequest;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.Line;
import com.eghm.model.LineDayConfig;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.line.LineDayConfigResponse;
import com.eghm.vo.business.line.LineDetailResponse;
import com.eghm.vo.business.line.LineResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Api(tags = "线路")
@AllArgsConstructor
@RequestMapping("/manage/line")
public class LineController {

    private final LineService lineService;

    private final LineDayConfigService lineDayConfigService;

    @ApiOperation("查询线路列表")
    @GetMapping("/listPage")
    public RespBody<PageData<LineResponse>> getByPage(LineQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LineResponse> scenicPage = lineService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @ApiOperation("创建线路")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody LineAddRequest request) {
        lineService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新线路")
    public RespBody<Void> update(@Validated @RequestBody LineEditRequest request) {
        lineService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<LineDetailResponse> select(@Validated IdDTO request) {
        Line line = lineService.selectByIdRequired(request.getId());
        LineDetailResponse response = DataUtil.copy(line, LineDetailResponse.class);
        List<LineDayConfig> dayList = lineDayConfigService.getByLineId(request.getId());
        response.setDayList(DataUtil.copy(dayList, LineDayConfigResponse.class));
        // 虚拟销量需要计算
        response.setVirtualNum(line.getTotalNum() - line.getSaleNum());
        return RespBody.success(response);
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        lineService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        lineService.deleteById(dto.getId());
        return RespBody.success();
    }

    @ApiOperation("导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response, LineQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<LineResponse> byPage = lineService.getList(request);
        ExcelUtil.export(response, "线路信息", byPage, LineResponse.class);
    }
}
