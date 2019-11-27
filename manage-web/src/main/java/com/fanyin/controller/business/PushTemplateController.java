package com.fanyin.controller.business;

import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.dao.model.system.TagView;
import com.fanyin.model.dto.business.push.PushTemplateEditRequest;
import com.fanyin.model.dto.business.push.PushTemplateQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.PushTemplateService;
import com.fanyin.service.common.TagViewService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:31
 */
@Controller
public class PushTemplateController {

    @Autowired
    private PushTemplateService pushTemplateService;

    @Autowired
    private TagViewService tagViewService;

    /**
     * 分页查询推送消息模板信息
     */
    @PostMapping("/business/push_template/list_page")
    @ResponseBody
    public Paging<PushTemplate> listPage(PushTemplateQueryRequest request){
        PageInfo<PushTemplate> byPage = pushTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 推送模板编辑页面
     */
    @GetMapping("/business/push_template/edit_page")
    public String editPage(Model model, Integer id){
        PushTemplate template = pushTemplateService.getById(id);
        List<TagView> list = tagViewService.getList();
        model.addAttribute("template",template);
        model.addAttribute("view",list);
        return "business/push_template/edit_page";
    }

    /**
     * 推送模板编辑保存
     */
    @PostMapping("/business/push_template/edit")
    @ResponseBody
    public RespBody edit(PushTemplateEditRequest request){
        pushTemplateService.editPushTemplate(request);
        return RespBody.getInstance();
    }

}
