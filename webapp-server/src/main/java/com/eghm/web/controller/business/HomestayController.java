package com.eghm.web.controller.business;

import com.eghm.dto.business.homestay.HomestayDTO;
import com.eghm.dto.business.homestay.HomestayQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.HomestayService;
import com.eghm.vo.business.homestay.HomestayDetailVO;
import com.eghm.vo.business.homestay.HomestayVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name="民宿")
@AllArgsConstructor
@RequestMapping(value = "/webapp/homestay", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayController {

    private final HomestayService homestayService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<HomestayVO>> listPage(@Validated HomestayQueryDTO dto) {
        List<HomestayVO> byPage = homestayService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<HomestayDetailVO> detail(@Validated HomestayDTO dto) {
        HomestayDetailVO detail = homestayService.detailById(dto);
        return RespBody.success(detail);
    }
}
