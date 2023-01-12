package com.eghm.web.controller.business;

import com.eghm.model.HomestayRoom;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Api(tags = "房态设置")
@AllArgsConstructor
@RequestMapping("/manage/homestay/room/config")
public class HomestayRoomConfigController {

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final HomestayRoomService homestayRoomService;

    @PostMapping("/setup")
    @ApiOperation("设置房态")
    public RespBody<Void> setup(@Validated @RequestBody RoomConfigRequest request) {
        HomestayRoom room = homestayRoomService.selectByIdShelve(request.getRoomId());
        request.setHomestayId(room.getHomestayId());
        homestayRoomConfigService.setup(request);
        return RespBody.success();
    }

    @GetMapping("/month")
    @ApiOperation("房态信息(月)")
    public List<RoomConfigResponse> getList(@Validated RoomConfigQueryRequest request) {
        return homestayRoomConfigService.getList(request);
    }

    @PostMapping("/update")
    @ApiOperation("更新房态")
    public RespBody<Void> update(@Validated @RequestBody RoomConfigEditRequest request) {
        homestayRoomConfigService.update(request);
        return RespBody.success();
    }


}
