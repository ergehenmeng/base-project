package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.user.UserScoreLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@RestController
@Api(tags = "用户积分")
@AllArgsConstructor
@RequestMapping("/score")
public class ScoreController {

    private final UserScoreLogService userScoreLogService;

    /**
     * 分页查询用户积分列表
     */
    @GetMapping("/listPage")
    @ApiOperation("用户积分列表")
    public RespBody<Paging<UserScoreVO>> listPage(@RequestBody @Valid UserScoreQueryDTO request) {
        request.setUserId(ApiHolder.getUserId());
        Paging<UserScoreVO> page = userScoreLogService.getByPage(request);
        return RespBody.success(page);
    }
}
