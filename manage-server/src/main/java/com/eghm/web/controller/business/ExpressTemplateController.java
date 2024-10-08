package com.eghm.web.controller.business;

import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.express.ExpressTemplateAddRequest;
import com.eghm.dto.business.item.express.ExpressTemplateEditRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ExpressTemplateService;
import com.eghm.vo.business.item.express.ExpressSelectResponse;
import com.eghm.vo.business.item.express.ExpressTemplateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@RestController
@Api(tags = "快递模板")
@AllArgsConstructor
@RequestMapping(value = "/manage/express/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpressTemplateController {

    private final ExpressTemplateService expressTemplateService;

    @GetMapping("/list")
    @ApiOperation("模板列表")
    public RespBody<List<ExpressTemplateResponse>> list() {
        List<ExpressTemplateResponse> byPage = expressTemplateService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(byPage);
    }

    @GetMapping("/selectList")
    @ApiOperation("模板列表(下拉专用)")
    @SkipPerm
    public RespBody<List<ExpressSelectResponse>> selectList() {
        List<ExpressSelectResponse> selectList = expressTemplateService.selectList(SecurityHolder.getMerchantId());
        return RespBody.success(selectList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody ExpressTemplateAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        expressTemplateService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody ExpressTemplateEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        expressTemplateService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        expressTemplateService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping(value = "/select")
    @ApiOperation("查询")
    public RespBody<ExpressTemplateResponse> select(@Validated IdDTO dto) {
        ExpressTemplateResponse selected = expressTemplateService.selectById(dto.getId());
        return RespBody.success(selected);
    }
}
