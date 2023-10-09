package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.vo.business.homestay.room.HomestayRoomListVO;
import com.eghm.vo.business.homestay.room.HomestayRoomVO;
import com.eghm.vo.business.homestay.room.config.RoomConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final HomestayRoomConfigService homestayRoomConfigService;

    @GetMapping("/listPage")
    @ApiOperation("房型列表")
    public RespBody<List<HomestayRoomListVO>> listPage(@Validated HomestayRoomQueryDTO request) {
        List<HomestayRoomListVO> listPage = homestayRoomService.listPage(request);
        return RespBody.success(listPage);
    }

    @GetMapping("/detail")
    @ApiOperation("房型详细信息")
    public RespBody<HomestayRoomVO> detail(@Validated IdDTO dto) {
        HomestayRoomVO detail = homestayRoomService.detailById(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/priceList")
    @ApiOperation("房型价格")
    public RespBody<List<RoomConfigVO>> priceList(@Validated IdDTO dto) {
        List<RoomConfigVO> voList = homestayRoomConfigService.getList(dto.getId());
        return RespBody.success(voList);
    }
}
