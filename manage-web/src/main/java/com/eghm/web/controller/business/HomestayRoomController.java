package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.model.dto.IdStateDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryRequest;
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
@RequestMapping("/homestay/room")
public class HomestayRoomController {

    private final HomestayRoomService homestayRoomService;

    @GetMapping("/listPage")
    @ApiOperation("房型列表")
    public PageData<HomestayRoom> listPage(HomestayRoomQueryRequest request) {
        Page<HomestayRoom> roomPage = homestayRoomService.getByPage(request);
        return PageData.toPage(roomPage);
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
    @ApiImplicitParam(name = "id", value = "房型信息", required = true)
    public HomestayRoom select(@RequestParam("id") Long id) {
        return homestayRoomService.selectById(id);
    }

    @PostMapping("/updateState")
    @ApiOperation("更新上下架状态")
    public RespBody<Void> updateState(@Validated @RequestBody IdStateDTO dto) {
        homestayRoomService.updateState(dto.getId(), dto.getState());
        return RespBody.success();
    }

}
