package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.HomestayRoom;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.homestay.room.HomestayRoomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Api(tags = "民宿房型")
@AllArgsConstructor
@RequestMapping(value = "/manage/homestay/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayRoomController {

    private final HomestayRoomService homestayRoomService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<HomestayRoomResponse>> listPage(HomestayRoomQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayRoomResponse> roomPage = homestayRoomService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/productListPage")
    @ApiOperation("列表含店铺信息")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = homestayRoomService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody HomestayRoomAddRequest request) {
        homestayRoomService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody HomestayRoomEditRequest request) {
        homestayRoomService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<HomestayRoom> select(@Validated IdDTO dto) {
        HomestayRoom homestayRoom = homestayRoomService.selectById(dto.getId());
        return RespBody.success(homestayRoom);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("设置推荐房型")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.setRecommend(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, HomestayRoomQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<HomestayRoomResponse> byPage = homestayRoomService.getList(request);
        ExcelUtil.export(response, "房型信息", byPage, HomestayRoomResponse.class);
    }
}
