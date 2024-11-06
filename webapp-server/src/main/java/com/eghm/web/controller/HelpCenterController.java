package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.help.HelpQueryDTO;
import com.eghm.service.common.HelpCenterService;
import com.eghm.vo.help.HelpCenterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
@AllArgsConstructor
@RequestMapping(value = "/webapp/help", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public RespBody<List<HelpCenterVO>> list(@Validated HelpQueryDTO dto) {
        List<HelpCenterVO> voList = helpCenterService.list(dto);
        return RespBody.success(voList);
    }
}
