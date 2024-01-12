package com.eghm.web.controller.business;

import com.eghm.dto.business.collect.CollectDTO;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MemberCollectService;
import com.eghm.vo.business.collect.MemberCollectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/listPage")
    @ApiOperation("收藏列表")
    public RespBody<List<MemberCollectVO>> listPage(@RequestBody CollectQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<MemberCollectVO> byPage = memberCollectService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @PostMapping("/collect")
    @ApiOperation("收藏/取消收藏")
    public RespBody<Void> collect(@RequestBody @Validated CollectDTO dto) {
        memberCollectService.collect(dto.getCollectId(), dto.getCollectType());
        return RespBody.success();
    }

}
