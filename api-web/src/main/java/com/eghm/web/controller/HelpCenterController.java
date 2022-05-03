package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.help.HelpQueryDTO;
import com.eghm.model.vo.help.HelpCenterVO;
import com.eghm.service.common.HelpCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
@AllArgsConstructor
@RequestMapping("/help")
public class HelpCenterController {

    private final HelpCenterService helpCenterService;

    /**
     * 查询帮助信息
     */
    @GetMapping("/list")
    @ApiOperation("帮助列表信息")
    public RespBody<List<HelpCenterVO>> list(@RequestBody @Valid HelpQueryDTO dto) {
        List<HelpCenterVO> voList = helpCenterService.list(dto);
        return RespBody.success(voList);
    }
}
