package com.eghm.web.controller;

import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CommonService;
import com.eghm.vo.sys.SysAreaVO;
import com.google.common.collect.Lists;
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

    private final CommonService commonService;

    @ApiOperation("获取省市区列表")
    @GetMapping("/list")
    @SkipPerm
    public RespBody<List<SysAreaVO>> list() {
        List<SysAreaVO> voList = commonService.getTreeAreaList();
        return RespBody.success(voList);
    }

    @ApiOperation("获取省市列表")
    @GetMapping("/provinceList")
    @SkipPerm
    public RespBody<List<SysAreaVO>> provinceList() {
        List<SysAreaVO> voList = commonService.getTreeAreaList(Lists.newArrayList(1, 2));
        return RespBody.success(voList);
    }
}
