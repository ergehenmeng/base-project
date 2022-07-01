package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Scenic;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.business.scenic.ScenicAddRequest;
import com.eghm.model.dto.business.scenic.ScenicEditRequest;
import com.eghm.model.dto.business.scenic.ScenicQueryRequest;
import com.eghm.service.business.ScenicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/17 19:06
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/scenic")
public class ScenicController {

    private final ScenicService scenicService;

    @ApiOperation("查询景区列表")
    @GetMapping("/listPage")
    public PageData<Scenic> getByPage(ScenicQueryRequest request) {
        Page<Scenic> scenicPage = scenicService.getByPage(request);
        return PageData.toPage(scenicPage);
    }

    @ApiOperation("创建景区")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody ScenicAddRequest request) {
        scenicService.createScenic(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新景区")
    public RespBody<Void> update(@Validated @RequestBody ScenicEditRequest request) {
        scenicService.updateScenic(request);
        return RespBody.success();
    }

    @PostMapping("/select")
    @ApiOperation("更新景区")
    public Scenic select(@Validated @RequestBody IdDTO request) {
        return scenicService.selectById(request.getId());
    }


}
