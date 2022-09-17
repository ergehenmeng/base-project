package com.eghm.web.controller;


import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.user.UserNoticeVO;
import com.eghm.service.user.UserNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 殿小二
 */
@RestController
@Api(tags = "站内信")
@AllArgsConstructor
@RequestMapping("/webapp/user/notice")
public class UserNoticeController {

    private final UserNoticeService userNoticeService;

    @GetMapping("/listPage")
    @ApiOperation("分页查询站内信列表")
    public RespBody<PageData<UserNoticeVO>> listPage(@RequestBody @Validated PagingQuery query) {
        PageData<UserNoticeVO> paging = userNoticeService.getByPage(query, ApiHolder.getUserId());
        return RespBody.success(paging);
    }

    @PostMapping("/delete")
    @ApiOperation("删除站内信")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        userNoticeService.deleteNotice(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/markRead")
    @ApiOperation("设置消息已读(消息未读时才调用)")
    public RespBody<Void> markRead(@RequestBody @Validated IdDTO dto) {
        userNoticeService.setNoticeRead(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

}
