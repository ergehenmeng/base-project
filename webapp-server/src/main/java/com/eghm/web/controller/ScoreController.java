package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.MemberScoreQueryDTO;
import com.eghm.service.member.MemberScoreLogService;
import com.eghm.vo.member.MemberScoreVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@AccessToken
@RestController
@Api(tags = "用户积分")
@AllArgsConstructor
@RequestMapping("/webapp/member/score")
public class ScoreController {

    private final MemberScoreLogService memberScoreLogService;

    @GetMapping("/listPage")
    @ApiOperation("用户积分列表")
    public RespBody<List<MemberScoreVO>> listPage(@Validated MemberScoreQueryDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        List<MemberScoreVO> page = memberScoreLogService.getByPage(request);
        return RespBody.success(page);
    }
}
