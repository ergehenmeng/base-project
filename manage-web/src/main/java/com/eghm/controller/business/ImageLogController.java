package com.eghm.controller.business;

import com.eghm.annotation.Mark;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.controller.AbstractController;
import com.eghm.dao.model.business.ImageLog;
import com.eghm.model.dto.business.image.ImageAddRequest;
import com.eghm.model.dto.business.image.ImageEditRequest;
import com.eghm.model.dto.business.image.ImageQueryRequest;
import com.eghm.model.ext.FilePath;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.common.FileService;
import com.eghm.service.common.ImageLogService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:13
 */
@Controller
public class ImageLogController extends AbstractController {

    @Autowired
    private ImageLogService imageLogService;

    @Autowired
    private CacheProxyService cacheProxyService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    /**
     * 图片列表页面
     */
    @GetMapping("/system/image/manage_page")
    public String managePage(Model model) {
        model.addAttribute("address", systemConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS));
        return "system/image/manage_page";
    }

    /**
     * 分页查询图片列表
     *
     * @return 分页数据
     */
    @PostMapping("/system/image/list_page")
    @ResponseBody
    @Mark
    public Paging<ImageLog> listPage(ImageQueryRequest request) {
        PageInfo<ImageLog> page = imageLogService.getByPage(request);
        return DataUtil.transform(page, imageLog -> {
            //将数据字典类型转换实际类型
            String dictValue = cacheProxyService.getDictValue(DictConstant.IMAGE_CLASSIFY, imageLog.getClassify());
            imageLog.setClassifyName(dictValue);
            return imageLog;
        });
    }

    /**
     * 添加图片
     *
     * @return 成功
     */
    @PostMapping("/system/image/add")
    @ResponseBody
    @Mark
    public RespBody<Object> addImage(ImageAddRequest request, MultipartFile imgFile) {
        if (imgFile != null && !imgFile.isEmpty()) {
            FilePath filePath = fileService.saveFile(imgFile);
            request.setUrl(filePath.getPath());
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
    @PostMapping("/system/image/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> editImage(ImageEditRequest request) {
        imageLogService.updateImageLog(request);
        return RespBody.success();
    }


    /**
     * 删除图片
     *
     * @param id 用户id
     * @return 删除
     */
    @PostMapping("/system/image/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> deleteImage(Integer id) {
        imageLogService.deleteImageLog(id);
        return RespBody.success();
    }

    /**
     * 图片编辑页面
     *
     * @return 图片地址
     */
    @GetMapping("/system/image/edit_page")
    @Mark
    public String editImagePage(Model model, Integer id) {
        ImageLog log = imageLogService.getById(id);
        model.addAttribute("log", log);
        return "system/image/edit_page";
    }
}
