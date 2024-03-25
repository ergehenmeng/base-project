package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.model.SysArea;
import com.eghm.cache.CacheProxyService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.SysAreaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/4
 */
@RestController
@Api(tags = "省份区域")
@AllArgsConstructor
@RequestMapping(value = "/webapp/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class AreaController {

    private final CacheProxyService cacheProxyService;

    @ApiOperation("获取省市区列表")
    @GetMapping("/getByPid")
    @ApiImplicitParam(name = "pid", value = "pid", required = true)
    public RespBody<List<SysAreaVO>> getByPid(@RequestParam("pid") Long pid) {
        List<SysArea> voList = cacheProxyService.getAreaByPid(pid);
        return RespBody.success(DataUtil.copy(voList, SysAreaVO.class));
    }

    @ApiOperation("获取省市区列表")
    @GetMapping("/list")
    public RespBody<List<SysAreaVO>> list() {
        List<SysAreaVO> voList = cacheProxyService.getAreaList();
        return RespBody.success(voList);
    }
}
