package com.eghm.web.controller.business;

import com.eghm.dto.business.collect.CollectDTO;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MemberCollectService;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@RestController
@Tag(name= "收藏商品")
@AllArgsConstructor
@RequestMapping(value = "/webapp/collect", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberCollectController {

    private final MemberCollectService memberCollectService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    @AccessToken
    public RespBody<List<MemberCollectVO>> listPage(CollectQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<MemberCollectVO> byPage = memberCollectService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @PostMapping(value = "/collect", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "收藏/取消收藏")
    @AccessToken
    public RespBody<Void> collect(@RequestBody @Validated CollectDTO dto) {
        memberCollectService.collect(dto.getCollectId(), dto.getCollectType());
        return RespBody.success();
    }

}
