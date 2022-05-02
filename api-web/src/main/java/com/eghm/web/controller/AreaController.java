package com.eghm.web.controller;

import com.eghm.model.dto.area.AreaQueryDTO;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api(tags = "省份区域")
@AllArgsConstructor
public class AreaController {

    private final SysAreaService sysAreaService;

    /**
     * 获取省市区列表
     */
    @ApiOperation("获取省市区列表")
    @GetMapping("/area/list")
    public RespBody<List<SysAreaVO>> list(@RequestBody @Valid AreaQueryDTO request) {
        List<SysAreaVO> voList = sysAreaService.getByPid(request.getPid());
        return RespBody.success(voList);
    }


}
