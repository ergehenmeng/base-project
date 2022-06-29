package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.homestay.room.RoomConfigResponse;
import com.eghm.service.business.HomestayRoomConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Api(tags = "房态设置")
@AllArgsConstructor
@RequestMapping("/homestay/room/config")
public class HomestayRoomConfigController {

    private final HomestayRoomConfigService homestayRoomConfigService;

    @PostMapping("/setup")
    @ApiOperation("设置房态")
    public RespBody<Void> setup(RoomConfigRequest request) {
        homestayRoomConfigService.setup(request);
        return RespBody.success();
    }

    @GetMapping("/month")
    @ApiOperation("房态信息(月)")
    public List<RoomConfigResponse> getList(RoomConfigQueryRequest request) {
        return homestayRoomConfigService.getList(request);
    }

}
