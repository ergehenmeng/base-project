package com.eghm.web.controller;

import com.eghm.model.dto.area.AreaQueryDTO;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api(tags = "省份区域")
public class AreaController {

    private SysAreaService sysAreaService;

    @Autowired
    @SkipLogger
    public void setSysAreaService(SysAreaService sysAreaService) {
        this.sysAreaService = sysAreaService;
    }

    /**
     * 获取省市区列表
     */
    @ApiOperation("获取省市区列表")
    @PostMapping("/area/list")
    public RespBody<List<SysAreaVO>> list(AreaQueryDTO request) {
        List<SysAreaVO> voList = sysAreaService.getByPid(request.getPid());
        return RespBody.success(voList);
    }


}
