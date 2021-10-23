package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.help.HelpQueryDTO;
import com.eghm.model.vo.help.HelpCenterVO;
import com.eghm.service.common.HelpCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/11/12
 */
@RestController
@Api(tags = "帮助中心")
public class HelpCenterController {

    @Autowired
    private HelpCenterService helpCenterService;

    /**
     * 查询帮助信息
     */
    @GetMapping("/help_center/list")
    @ApiOperation("帮助列表信息")
    public RespBody<Object> list(HelpQueryDTO dto) {
        List<HelpCenterVO> voList = helpCenterService.list(dto);
        return RespBody.success(voList);
    }
}
