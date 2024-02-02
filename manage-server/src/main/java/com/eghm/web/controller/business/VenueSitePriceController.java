package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.venue.VenueSitePriceRequest;
import com.eghm.dto.business.venue.VenueSitePriceUpdateRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VenueSitePriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@RestController
@Api(tags = "场地价格")
@AllArgsConstructor
@RequestMapping("/manage/venue/site/price")
public class VenueSitePriceController {

    private final VenueSitePriceService venueSitePriceService;

    @PostMapping("/setup")
    @ApiOperation("设置价格")
    public RespBody<Void> setup(@Validated @RequestBody VenueSitePriceRequest request) {
        venueSitePriceService.insertOrUpdate(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody VenueSitePriceUpdateRequest request) {
        venueSitePriceService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueSitePriceService.delete(dto.getId());
        return RespBody.success();
    }
}
