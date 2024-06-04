package com.eghm.web.controller;

import com.eghm.cache.CacheProxyService;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.sys.SysAreaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/6/4
 */
@RestController
@Api(tags = "省市县")
@AllArgsConstructor
@RequestMapping(value = "/manage/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysAreaController {

    private final CacheProxyService cacheProxyService;

    @ApiOperation("获取省市区列表")
    @GetMapping("/list")
    @SkipPerm
    public RespBody<List<SysAreaVO>> list() {
        List<SysAreaVO> voList = cacheProxyService.getAreaList();
        return RespBody.success(voList);
    }

}
