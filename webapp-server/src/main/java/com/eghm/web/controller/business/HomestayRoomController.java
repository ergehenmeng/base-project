package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryDTO;
import com.eghm.model.vo.business.homestay.room.HomestayRoomListVO;
import com.eghm.model.vo.business.homestay.room.HomestayRoomVO;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigVO;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.web.annotation.SkipAccess;
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
    @SkipAccess
    public List<HomestayRoomListVO> listPage(HomestayRoomQueryDTO request) {
        return homestayRoomService.listPage(request);
    }

    @GetMapping("/detail")
    @ApiOperation("房型详细信息")
    @SkipAccess
    public HomestayRoomVO detail(@Validated IdDTO dto) {
        return homestayRoomService.detailById(dto.getId());
    }

    @GetMapping("/priceList")
    @ApiOperation("房型价格")
    @SkipAccess
    public List<RoomConfigVO> priceList(@Validated IdDTO dto) {
        return homestayRoomConfigService.getList(dto.getId());
    }
}
