package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.user.UserScoreLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@RestController
@Api(tags = "用户积分")
@AllArgsConstructor
@RequestMapping("/webapp/user/score")
public class ScoreController {

    private final UserScoreLogService userScoreLogService;

    @GetMapping("/listPage")
    @ApiOperation("用户积分列表")
    public RespBody<PageData<UserScoreVO>> listPage(@Validated UserScoreQueryDTO request) {
        request.setUserId(ApiHolder.getUserId());
        PageData<UserScoreVO> page = userScoreLogService.getByPage(request);
        return RespBody.success(page);
    }
}
