package com.eghm.web.controller.business;

import com.eghm.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.HomestayRoom;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.vo.business.homestay.room.config.RoomConfigResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Tag(name="房态设置")
@AllArgsConstructor
@RequestMapping(value = "/manage/homestay/room/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayRoomConfigController {

    private final CommonService commonService;

    private final HomestayRoomService homestayRoomService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    @PostMapping(value = "/setup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置房态")
    public RespBody<Void> setup(@Validated @RequestBody RoomConfigRequest request) {
        HomestayRoom room = homestayRoomService.selectByIdRequired(request.getRoomId());
        commonService.checkIllegal(room.getMerchantId());
        request.setHomestayId(room.getHomestayId());
        homestayRoomConfigService.setup(request);
        return RespBody.success();
    }

    @GetMapping("/month")
    @Operation(summary = "房态信息(月)")
    public RespBody<List<RoomConfigResponse>> getList(@ParameterObject @Validated RoomConfigQueryRequest request) {
        homestayRoomService.selectByIdRequired(request.getRoomId());
        List<RoomConfigResponse> responseList = homestayRoomConfigService.getList(request);
        return RespBody.success(responseList);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody RoomConfigEditRequest request) {
        HomestayRoom room = homestayRoomService.selectById(request.getRoomId());
        commonService.checkIllegal(room.getMerchantId());
        request.setHomestayId(room.getHomestayId());
        homestayRoomConfigService.update(request);
        return RespBody.success();
    }

}
