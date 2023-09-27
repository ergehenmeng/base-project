package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.HomestayRoom;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.business.homestay.room.HomestayRoomResponse;
import com.eghm.service.business.HomestayRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Api(tags = "民宿房型")
@AllArgsConstructor
@RequestMapping("/manage/homestay/room")
public class HomestayRoomController {

    private final HomestayRoomService homestayRoomService;

    @GetMapping("/listPage")
    @ApiOperation("房型列表")
    public RespBody<PageData<HomestayRoomResponse>> listPage(HomestayRoomQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayRoomResponse> roomPage = homestayRoomService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
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
    public RespBody<HomestayRoom> select(@RequestParam("id") Long id) {
        HomestayRoom homestayRoom = homestayRoomService.selectById(id);
        return RespBody.success(homestayRoom);
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
    public RespBody<Void> platformAudit(@RequestBody @Validated IdDTO dto) {
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

    @PostMapping("/recommend")
    @ApiOperation("设置推荐房型")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.setRecommend(dto.getId());
        return RespBody.success();
    }
}
