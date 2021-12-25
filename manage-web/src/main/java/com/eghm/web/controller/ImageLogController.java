package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.ImageLog;
import com.eghm.model.dto.ext.FilePath;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.image.ImageAddRequest;
import com.eghm.model.dto.image.ImageEditRequest;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.common.FileService;
import com.eghm.service.common.ImageLogService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:13
 */
@RestController
@Api(tags = "图片管理")
public class ImageLogController {

    private ImageLogService imageLogService;

    private ProxyService proxyService;

    private FileService fileService;

    private SysConfigApi sysConfigApi;

    @Autowired
    public void setImageLogService(ImageLogService imageLogService) {
        this.imageLogService = imageLogService;
    }

    @Autowired
    public void setProxyService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    /**
     * 图片列表页面
     */
    @GetMapping("/image/manage_page")
    public String managePage(Model model) {
        model.addAttribute("address", sysConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS));
        return "image/manage_page";
    }

    /**
     * 分页查询图片列表
     *
     * @return 分页数据
     */
    @GetMapping("/image/list_page")
    @ApiOperation("图片列表(分页)")
    public Paging<ImageLog> listPage(ImageQueryRequest request) {
        Page<ImageLog> page = imageLogService.getByPage(request);
        ProxyService finalProxy = this.proxyService;
        return DataUtil.convert(page, imageLog -> {
            //将数据字典类型转换实际类型
            String dictValue = finalProxy.getDictValue(DictConstant.IMAGE_CLASSIFY, imageLog.getClassify());
            imageLog.setClassifyName(dictValue);
            return imageLog;
        });
    }

    /**
     * 添加图片
     *
     * @return 成功
     */
    @PostMapping("/image/add")
    @Mark
    @ApiOperation("添加图片")
    @ApiImplicitParam(name = "imgFile", value = "文件流", required = true, paramType = "formData", dataType = "file")
    public RespBody<Object> addImage(@Valid ImageAddRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        if (imgFile != null && !imgFile.isEmpty()) {
            FilePath filePath = fileService.saveFile(imgFile);
            request.setPath(filePath.getPath());
            request.setSize(imgFile.getSize());
        }
        imageLogService.addImageLog(request);
        return RespBody.success();
    }

    /**
     * 编辑图片信息,不让重新上传因为图片可能已经被其他地方引用了
     *
     * @param request 更新参数
     * @return 成功
     */
    @PostMapping("/image/edit")
    @Mark
    @ApiOperation("添加图片")
    public RespBody<Object> editImage(@Valid ImageEditRequest request) {
        imageLogService.updateImageLog(request);
        return RespBody.success();
    }


    /**
     * 删除图片
     *
     * @param id 用户id
     * @return 删除
     */
    @PostMapping("/image/delete")
    @Mark
    @ApiOperation("删除图片")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Object> deleteImage(@RequestParam("id") Long id) {
        imageLogService.deleteImageLog(id);
        return RespBody.success();
    }

    /**
     * 图片编辑页面
     *
     * @return 图片地址
     */
    @GetMapping("/image/edit_page")
    public String editImagePage(Model model, Long id) {
        ImageLog log = imageLogService.getById(id);
        model.addAttribute("log", log);
        return "image/edit_page";
    }
}
