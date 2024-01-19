package com.eghm.web.controller.business;

import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ExpressTemplateService;
import com.eghm.vo.business.item.express.ExpressSelectResponse;
import com.eghm.vo.business.item.express.ItemExpressResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/manage/express/template")
public class ItemExpressController {

    private final ExpressTemplateService expressTemplateService;

    @GetMapping("/list")
    @ApiOperation("模板列表")
    public RespBody<List<ItemExpressResponse>> list() {
        List<ItemExpressResponse> byPage = expressTemplateService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(byPage);
    }

    @GetMapping("/selectList")
    @ApiOperation("模板列表(下拉专用)")
    @SkipPerm
    public RespBody<List<ExpressSelectResponse>> selectList() {
        List<ExpressSelectResponse> selectList = expressTemplateService.selectList(SecurityHolder.getMerchantId());
        return RespBody.success(selectList);
    }

    @PostMapping("/create")
    @ApiOperation("新增模板")
    public RespBody<Void> create(@Validated @RequestBody ItemExpressAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        expressTemplateService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新模板")
    public RespBody<Void> update(@Validated @RequestBody ItemExpressEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        expressTemplateService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除模板")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        expressTemplateService.deleteById(dto.getId());
        return RespBody.success();
    }

}
