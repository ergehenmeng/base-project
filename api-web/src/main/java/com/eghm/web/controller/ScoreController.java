package com.eghm.web.controller;

import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.user.UserScoreLogService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@RestController
@Api(tags = "用户积分")
public class ScoreController {

    private UserScoreLogService userScoreLogService;

    @Autowired
    @SkipLogger
    public void setUserScoreLogService(UserScoreLogService userScoreLogService) {
        this.userScoreLogService = userScoreLogService;
    }

    /**
     * 分页查询用户积分列表
     */
    @PostMapping("/score/list_page")
    @ApiOperation("用户积分列表")
    public RespBody<Paging<UserScoreVO>> listPage(UserScoreQueryDTO request) {
        request.setUserId(ApiHolder.getUserId());
        Paging<UserScoreVO> page = userScoreLogService.getByPage(request);
        return RespBody.success(page);
    }
}
