package com.eghm.web.controller.business;

import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.business.item.ItemTagResponse;
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
 * @since 2023/8/9
 */
@RestController
@Api(tags = "零售标签")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemTagController {

    private final CacheProxyService cacheProxyService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public RespBody<List<ItemTagResponse>> list() {
        List<ItemTagResponse> serviceList = cacheProxyService.getList();
        return RespBody.success(serviceList);
    }

}
