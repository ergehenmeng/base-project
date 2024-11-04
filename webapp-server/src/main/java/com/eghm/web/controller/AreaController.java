package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.common.CommonService;
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
 * 注意: 省市区信息不在管理后台维护,均以数据库维护为准, 修改后需要刷新缓存
 *
 * @author 殿小二
 * @since 2020/9/4
 */
@RestController
@Api(tags = "省份区域")
@AllArgsConstructor
@RequestMapping(value = "/webapp/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class AreaController {

    private final CommonService commonService;

    @ApiOperation("获取省市区列表")
    @GetMapping("/list")
    public RespBody<List<SysAreaVO>> list() {
        List<SysAreaVO> voList = commonService.getTreeAreaList();
        return RespBody.success(voList);
    }
}
