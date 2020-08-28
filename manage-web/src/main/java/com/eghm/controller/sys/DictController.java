package com.eghm.controller.sys;

import com.eghm.annotation.Mark;
import com.eghm.dao.model.system.SysDict;
import com.eghm.model.dto.sys.dict.DictAddRequest;
import com.eghm.model.dto.sys.dict.DictEditRequest;
import com.eghm.model.dto.sys.dict.DictQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.sys.SysDictService;
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
    @PostMapping("/sys/dict/list_page")
    @ResponseBody
    @Mark
    public Paging<SysDict> listPage(DictQueryRequest request) {
        return new Paging<>(sysDictService.getByPage(request));
    }

    /**
     * 编辑数据字典页面
     *
     * @param id id
     * @return 页面地址
     */
    @GetMapping("/sys/dict/edit_page")
    @Mark
    public String editPage(Model model, Integer id) {
        SysDict dict = sysDictService.getById(id);
        model.addAttribute("dict", dict);
        return "sys/dict/edit_page";
    }

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     * @return 成功响应
     */
    @PostMapping("/sys/dict/add")
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
    @PostMapping("/sys/dict/edit")
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
    @PostMapping("/sys/dict/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Integer id) {
        sysDictService.deleteDict(id);
        return RespBody.success();
    }

}
