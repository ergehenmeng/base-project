package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.venue.VenueSitePriceRequest;
import com.eghm.dto.business.venue.VenueSitePriceUpdateRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.VenueSite;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.VenueSiteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name="场地价格")
@AllArgsConstructor
@RequestMapping(value = "/manage/venue/site/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueSitePriceController {

    private final VenueSiteService venueSiteService;

    private final VenueSitePriceService venueSitePriceService;

    @PostMapping(value = "/setup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置价格")
    public RespBody<Void> setup(@Validated @RequestBody VenueSitePriceRequest request) {
        VenueSite venueSite = venueSiteService.selectByIdRequired(request.getVenueSiteId());
        request.setVenueId(venueSite.getVenueId());
        venueSitePriceService.insertOrUpdate(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody VenueSitePriceUpdateRequest request) {
        venueSitePriceService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueSitePriceService.delete(dto.getId());
        return RespBody.success();
    }
}
