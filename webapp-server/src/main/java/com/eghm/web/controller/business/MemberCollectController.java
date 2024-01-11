package com.eghm.web.controller.business;

import com.eghm.dto.business.collect.CollectDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MemberCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@RestController
@Api(tags = "收藏商品")
@AllArgsConstructor
@RequestMapping("/webapp/collect")
public class MemberCollectController {

    private final MemberCollectService memberCollectService;

    @PostMapping("/collect")
    @ApiOperation("收藏/取消收藏")
    public RespBody<Void> collect(@RequestBody @Validated CollectDTO dto) {
        memberCollectService.checkCollect(dto.getCollectId(), dto.getCollectType());
        return RespBody.success();
    }

}
