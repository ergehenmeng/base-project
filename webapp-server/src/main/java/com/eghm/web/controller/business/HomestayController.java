package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.homestay.HomestayQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.HomestayService;
import com.eghm.vo.business.homestay.HomestayDetailVO;
import com.eghm.vo.business.homestay.HomestayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */
@RestController
@Api(tags = "民宿")
@AllArgsConstructor
@RequestMapping("/webapp/homestay")
public class HomestayController {

    private final HomestayService homestayService;

    @GetMapping("/listPage")
    @ApiOperation("民宿列表")
    public RespBody<List<HomestayVO>> listPage(@Validated HomestayQueryDTO dto) {
        List<HomestayVO> byPage = homestayService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("民宿详情")
    public RespBody<HomestayDetailVO> detail(@Validated IdDTO dto) {
        HomestayDetailVO detail = homestayService.detailById(dto.getId());
        return RespBody.success(detail);
    }
}
