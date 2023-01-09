package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Homestay;
import com.eghm.model.dto.business.homestay.HomestayQueryDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.vo.business.homestay.HomestayListVO;
import com.eghm.service.business.HomestayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
    public List<HomestayListVO> listPage(HomestayQueryDTO dto) {
        return homestayService.getByPage(dto);
    }

}
