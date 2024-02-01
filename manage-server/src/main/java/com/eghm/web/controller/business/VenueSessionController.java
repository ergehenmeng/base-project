package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.venue.VenueSessionAddRequest;
import com.eghm.dto.business.venue.VenueSessionEditRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VenueSessionService;
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
@Api(tags = "场馆场次")
@AllArgsConstructor
@RequestMapping("/manage/venue/session")
public class VenueSessionController {

    private final VenueSessionService venueSessionService;

    @PostMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody VenueSessionAddRequest request) {
        venueSessionService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody VenueSessionEditRequest request) {
        venueSessionService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueSessionService.delete(dto.getId());
        return RespBody.success();
    }
}
