package com.eghm.web.controller.business;

import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.HomestayRoom;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryDTO;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.homestay.room.HomestayRoomListVO;
import com.eghm.service.business.HomestayRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛 2023/1/9
 */
@RestController
@Api(tags = "房型")
@AllArgsConstructor
@RequestMapping("/webapp/homestay/room")
public class HomestayRoomController {

    private final HomestayRoomService homestayRoomService;

    @GetMapping("/listPage")
    @ApiOperation("房型列表")
    public List<HomestayRoomListVO> listPage(HomestayRoomQueryDTO request) {
        return homestayRoomService.listPage(request);
    }

    @PostMapping("/create")
    @ApiOperation("新增房型")
    public RespBody<Void> create(@Validated @RequestBody HomestayRoomAddRequest request) {
        homestayRoomService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新房型")
    public RespBody<Void> update(@Validated @RequestBody HomestayRoomEditRequest request) {
        homestayRoomService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询房型")
    @ApiImplicitParam(name = "id", value = "店铺id", required = true)
    public HomestayRoom select(@RequestParam("id") Long id) {
        return homestayRoomService.selectById(id);
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.deleteById(dto.getId());
        return RespBody.success();
    }
}
