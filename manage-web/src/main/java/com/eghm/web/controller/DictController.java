package com.eghm.web.controller;

import com.eghm.dao.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysDictService;
import com.eghm.web.annotation.Mark;
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

    private SysDictService sysDictService;

    @Autowired
    public void setSysDictService(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    /**
     * 分页查询数据字典列表
     *
     * @param request 前台参数
     * @return 分页列表
     */
    @GetMapping("/dict/list_page")
    @ResponseBody
    public Paging<SysDict> listPage(DictQueryRequest request) {
        return new Paging<>(sysDictService.getByPage(request));
    }

    /**
     * 编辑数据字典页面
     *
     * @param id id
     * @return 页面地址
     */
    @GetMapping("/dict/edit_page")
    public String editPage(Model model, Long id) {
        SysDict dict = sysDictService.getById(id);
        model.addAttribute("dict", dict);
        return "dict/edit_page";
    }

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     * @return 成功响应
     */
    @PostMapping("/dict/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(DictAddRequest request) {
        sysDictService.addDict(request);
        return RespBody.success();
    }

    /**
     * 编辑数据字典
     *
     * @param request 前台参数
     * @return 结果
     */
    @PostMapping("/dict/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(DictEditRequest request) {
        sysDictService.updateDict(request);
        return RespBody.success();
    }


    /**
     * 删除数据字典项
     *
     * @param id 主键
     * @return 成功响应
     */
    @PostMapping("/dict/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Long id) {
        sysDictService.deleteDict(id);
        return RespBody.success();
    }

}
