package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.MemberScoreQueryDTO;
import com.eghm.service.member.MemberScoreLogService;
import com.eghm.vo.member.MemberScoreVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name="用户积分")
@AllArgsConstructor
@RequestMapping(value = "/webapp/member/score", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreController {

    private final MemberScoreLogService memberScoreLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<MemberScoreVO>> listPage(MemberScoreQueryDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        List<MemberScoreVO> page = memberScoreLogService.clientByPage(request);
        return RespBody.success(page);
    }
}
