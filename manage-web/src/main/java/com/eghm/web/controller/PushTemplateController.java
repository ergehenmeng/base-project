package com.eghm.web.controller;

import com.eghm.dao.model.PushTemplate;
import com.eghm.dao.model.TagView;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
import com.eghm.service.common.TagViewService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:31
 */
@RestController
@Api(tags = "消息模板管理")
public class PushTemplateController {

    private PushTemplateService pushTemplateService;

    private TagViewService tagViewService;

    @Autowired
    public void setPushTemplateService(PushTemplateService pushTemplateService) {
        this.pushTemplateService = pushTemplateService;
    }

    @Autowired
    public void setTagViewService(TagViewService tagViewService) {
        this.tagViewService = tagViewService;
    }

    /**
     * 分页查询推送消息模板信息
     */
    @GetMapping("/push_template/list_page")
    @ApiOperation("消息模板列表")
    public Paging<PushTemplate> listPage(PushTemplateQueryRequest request) {
        PageInfo<PushTemplate> byPage = pushTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 推送模板编辑页面
     */
    @GetMapping("push_template/edit_page")
    public String editPage(Model model, Long id) {
        PushTemplate template = pushTemplateService.getById(id);
        List<TagView> list = tagViewService.getList();
        model.addAttribute("template", template);
        model.addAttribute("view", list);
        return "push_template/edit_page";
    }

    /**
     * 推送模板编辑保存
     */
    @PostMapping("/push_template/edit")
    @Mark
    @ApiOperation("编辑推送模板")
    public RespBody<Object> edit(@Valid PushTemplateEditRequest request) {
        pushTemplateService.editPushTemplate(request);
        return RespBody.success();
    }

}
