package com.fanyin.controller.system;

import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.dto.system.dict.DictAddRequest;
import com.fanyin.dto.system.dict.DictEditRequest;
import com.fanyin.dto.system.dict.DictQueryRequest;
import com.fanyin.ext.Paging;
import com.fanyin.ext.RespBody;
import com.fanyin.model.system.SystemDict;
import com.fanyin.service.system.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     * @param request 前台参数
     * @return 分页列表
     */
    @PostMapping("/system/dict/list_page")
    @ResponseBody
    @Mark(RequestType.SELECT)
    public Paging<SystemDict> listPage(DictQueryRequest request){
        return new Paging<>(systemDictService.getByPage(request));
    }

    /**
     * 编辑数据字典页面
     * @param id id
     * @return 页面地址
     */
    @PostMapping("/public/system/dict/edit_page")
    @Mark(RequestType.PAGE)
    public String editPage(Model model,Integer id){
        SystemDict dict = systemDictService.getById(id);
        model.addAttribute("dict",dict);
        return "public/system/dict/edit_page";
    }

    /**
     * 添加数据字典
     * @param request 前台参数
     * @return 成功响应
     */
    @PostMapping("/system/dict/add")
    @ResponseBody
    @Mark(RequestType.INSERT)
    public RespBody add(DictAddRequest request){
        systemDictService.addDict(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑数据字典
     * @param request 前台参数
     * @return 结果
     */
    @PostMapping("/system/dict/edit")
    @ResponseBody
    @Mark(RequestType.UPDATE)
    public RespBody edit(DictEditRequest request){
        systemDictService.updateDict(request);
        return RespBody.getInstance();
    }


    /**
     * 删除数据字典项
     * @param id 主键
     * @return 成功响应
     */
    @PostMapping("/system/dict/delete")
    @ResponseBody
    @Mark(RequestType.DELETE)
    public RespBody delete(Integer id){
        systemDictService.deleteDict(id);
        return RespBody.getInstance();
    }

}
