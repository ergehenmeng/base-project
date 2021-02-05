package com.eghm.web.controller;


import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.user.UserNoticeVO;
import com.eghm.service.user.UserNoticeService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 */
@RestController
@Api(tags = "站内信")
public class UserNoticeController {

    private UserNoticeService userNoticeService;

    @Autowired
    @SkipLogger
    public void setUserNoticeService(UserNoticeService userNoticeService) {
        this.userNoticeService = userNoticeService;
    }

    /**
     * 分页查询用户站内信通知信息
     */
    @GetMapping("/notice/list_page")
    @ApiOperation("分页查询站内信列表")
    public RespBody<Paging<UserNoticeVO>> listPage(PagingQuery query) {
        Paging<UserNoticeVO> paging = userNoticeService.getByPage(query, ApiHolder.getUserId());
        return RespBody.success(paging);
    }

    /**
     * 删除站内信
     */
    @PostMapping("/notice/delete")
    @ApiOperation("删除站内信")
    public RespBody<Object> delete(IdDTO dto) {
        userNoticeService.deleteNotice(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 设置消息已读
     */
    @PostMapping("/notice/set_read")
    @ApiOperation("设置消息已读(消息未读时才调用)")
    public RespBody<Object> setRead(IdDTO dto) {
        userNoticeService.setNoticeRead(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

}
