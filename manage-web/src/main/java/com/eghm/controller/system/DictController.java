package com.eghm.controller.system;

import com.eghm.annotation.Mark;
import com.eghm.model.dto.system.dict.DictAddRequest;
import com.eghm.model.dto.system.dict.DictEditRequest;
import com.eghm.model.dto.system.dict.DictQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.dao.model.system.SystemDict;
import com.eghm.service.system.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:10
 */
@Controller
public class DictController {

    @Autowired
    private SystemDictService systemDictService;

    /**
     * 分页查询数据字典列表
     *
     * @param request 前台参数
     * @return 分页列表
     */
    @PostMapping("/system/dict/list_page")
    @ResponseBody
    @Mark
    public Paging<SystemDict> listPage(DictQueryRequest request) {
        return new Paging<>(systemDictService.getByPage(request));
    }

    /**
     * 编辑数据字典页面
     *
     * @param id id
     * @return 页面地址
     */
    @GetMapping("/system/dict/edit_page")
    @Mark
    public String editPage(Model model, Integer id) {
        SystemDict dict = systemDictService.getById(id);
        model.addAttribute("dict", dict);
        return "system/dict/edit_page";
    }

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     * @return 成功响应
     */
    @PostMapping("/system/dict/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(DictAddRequest request) {
        systemDictService.addDict(request);
        return RespBody.success();
    }

    /**
     * 编辑数据字典
     *
     * @param request 前台参数
     * @return 结果
     */
    @PostMapping("/system/dict/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(DictEditRequest request) {
        systemDictService.updateDict(request);
        return RespBody.success();
    }


    /**
     * 删除数据字典项
     *
     * @param id 主键
     * @return 成功响应
     */
    @PostMapping("/system/dict/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Integer id) {
        systemDictService.deleteDict(id);
        return RespBody.success();
    }

}
