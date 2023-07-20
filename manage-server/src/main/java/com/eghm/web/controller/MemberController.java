package com.eghm.web.controller;

import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.MemberQueryRequest;
import com.eghm.service.member.MemberService;
import com.eghm.vo.member.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2023/8/18 14:16
 */
@RestController
@Api(tags = "会员信息")
@AllArgsConstructor
@RequestMapping("/manage/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MemberResponse>> listPage(MemberQueryRequest request) {
        PageData<MemberResponse> byPage = memberService.getByPage(request);
        return RespBody.success(byPage);
    }

}
