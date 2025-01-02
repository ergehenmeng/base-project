package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.help.HelpQueryDTO;
import com.eghm.service.operate.HelpCenterService;
import com.eghm.vo.operate.help.HelpCenterVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name= "帮助中心")
@AllArgsConstructor
@RequestMapping(value = "/webapp/help", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    @GetMapping("/list")
    @Operation(summary = "列表")
    public RespBody<List<HelpCenterVO>> list(@ParameterObject @Validated HelpQueryDTO dto) {
        List<HelpCenterVO> voList = helpCenterService.list(dto);
        return RespBody.success(voList);
    }
}
