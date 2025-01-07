package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.RecommendDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.HomestayRoom;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.homestay.room.HomestayRoomDetailResponse;
import com.eghm.vo.business.homestay.room.HomestayRoomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/29
 */
@RestController
@Tag(name="民宿房型")
@AllArgsConstructor
@RequestMapping(value = "/manage/homestay/room", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayRoomController {

    private final HomestayRoomService homestayRoomService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<HomestayRoomResponse>> listPage(HomestayRoomQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayRoomResponse> roomPage = homestayRoomService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/productListPage")
    @Operation(summary = "列表(含店铺)")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = homestayRoomService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody HomestayRoomAddRequest request) {
        homestayRoomService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody HomestayRoomEditRequest request) {
        homestayRoomService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<HomestayRoomDetailResponse> select(@Validated IdDTO dto) {
        HomestayRoom homestayRoom = homestayRoomService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(homestayRoom, HomestayRoomDetailResponse.class));
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        homestayRoomService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置推荐房型")
    public RespBody<Void> recommend(@RequestBody @Validated RecommendDTO dto) {
        homestayRoomService.setRecommend(dto.getId(), dto.getRecommend());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, HomestayRoomQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<HomestayRoomResponse> byPage = homestayRoomService.getList(request);
        EasyExcelUtil.export(response, "房型信息", byPage, HomestayRoomResponse.class);
    }
}
