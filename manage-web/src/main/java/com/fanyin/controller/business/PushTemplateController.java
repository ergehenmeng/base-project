package com.fanyin.controller.business;

import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.model.dto.business.push.PushTemplateQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.common.PushTemplateService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:31
 */
@Controller
public class PushTemplateController {

    @Autowired
    private PushTemplateService pushTemplateService;

    /**
     * 分页查询推送消息模板信息
     */
    @RequestMapping("/business/push_template/list_page")
    public Paging<PushTemplate> listPage(PushTemplateQueryRequest request){
        PageInfo<PushTemplate> byPage = pushTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }



}
