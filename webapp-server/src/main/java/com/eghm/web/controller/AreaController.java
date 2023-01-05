package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api(tags = "省份区域")
@AllArgsConstructor
@RequestMapping("/webapp/area")
public class AreaController {

    private final SysAreaService sysAreaService;

    @ApiOperation("获取省市区列表")
    @GetMapping("/list")
    @ApiImplicitParam(name = "pid", value = "pid")
    public RespBody<List<SysAreaVO>> list(@NotNull(message = "pid不能为空") @RequestParam(value = "pid") Long pid) {
        List<SysAreaVO> voList = sysAreaService.getByPid(pid);
        return RespBody.success(voList);
    }

}
