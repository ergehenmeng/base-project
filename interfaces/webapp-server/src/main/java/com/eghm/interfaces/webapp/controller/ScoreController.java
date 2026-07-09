package com.eghm.interfaces.webapp.controller;

import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.business.member.MemberScoreQueryDTO;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.member.port.in.MemberScoreLogService;
import com.eghm.application.shared.vo.business.member.MemberScoreVO;
import com.eghm.interfaces.webapp.annotation.AccessToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public RespBody<List<MemberScoreVO>> listPage(@ParameterObject MemberScoreQueryDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        List<MemberScoreVO> page = memberScoreLogService.clientByPage(request);
        return RespBody.success(page);
    }
}
