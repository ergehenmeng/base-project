package com.eghm.web.controller.business;

import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.ItemTagAddRequest;
import com.eghm.dto.business.item.ItemTagEditRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemTagService;
import com.eghm.vo.business.item.ItemTagResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/9
 */
@RestController
@Api(tags = "零售标签")
@AllArgsConstructor
@RequestMapping(value = "/manage/item/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemTagController {

    private final ItemTagService itemTagService;

    @GetMapping("/list")
    @ApiOperation("标签列表")
    @SkipPerm
    public RespBody<List<ItemTagResponse>> list() {
        List<ItemTagResponse> serviceList = itemTagService.getList();
        return RespBody.success(serviceList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增标签")
    public RespBody<Void> create(@Validated @RequestBody ItemTagAddRequest request) {
        itemTagService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑标签")
    public RespBody<Void> update(@Validated @RequestBody ItemTagEditRequest request) {
        itemTagService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除标签")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        itemTagService.deleteById(dto.getId());
        return RespBody.success();
    }

}
