package com.eghm.controller;

import com.eghm.model.dto.sys.area.AreaQueryRequest;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.sys.SysAreaService;
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
@Api("省份区域")
public class SysAreaController {

    private SysAreaService sysAreaService;

    @Autowired
    public void setSysAreaService(SysAreaService sysAreaService) {
        this.sysAreaService = sysAreaService;
    }

    /**
     * 获取省市区列表
     */
    @ApiOperation("获取省市区列表")
    @PostMapping("/area/list")
    public RespBody<Object> list(AreaQueryRequest request) {
        List<SysAreaVO> voList = sysAreaService.getByPid(request.getPid());
        return RespBody.success(voList);
    }


}
