package com.fanyin.controller.business;

import com.fanyin.annotation.Mark;
import com.fanyin.annotation.RequestType;
import com.fanyin.constants.DictConstant;
import com.fanyin.controller.AbstractController;
import com.fanyin.dao.model.business.ImageLog;
import com.fanyin.model.dto.business.image.ImageAddRequest;
import com.fanyin.model.dto.business.image.ImageEditRequest;
import com.fanyin.model.dto.business.image.ImageQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.service.common.ImageLogService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * 分页查询图片列表
     * @return 分页数据
     */
    @PostMapping("/system/image/list_page")
    @ResponseBody
    @Mark(RequestType.SELECT)
    public Paging<ImageLog> listPage(ImageQueryRequest request){
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
     * @return 成功
     */
    @PostMapping("/system/image/add")
    @ResponseBody
    @Mark(RequestType.INSERT)
    public RespBody addImage(ImageAddRequest request, MultipartFile imgFile){
        if(imgFile != null && !imgFile.isEmpty()){
            String url = super.saveFile(imgFile);
            request.setUrl(url);
            request.setSize(imgFile.getSize());
        }
        imageLogService.addImageLog(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑图片信息,不让重新上传因为图片可能已经被其他地方引用了
     * @param request 更新参数
     * @return 成功
     */
    @PostMapping("/system/image/edit")
    @ResponseBody
    @Mark(RequestType.UPDATE)
    public RespBody editImage(ImageEditRequest request){
        imageLogService.updateImageLog(request);
        return RespBody.getInstance();
    }


    /**
     * 删除图片
     * @param id 用户id
     * @return 删除
     */
    @PostMapping("/system/image/delete")
    @ResponseBody
    @Mark(RequestType.DELETE)
    public RespBody deleteImage(Integer id){
        imageLogService.deleteImageLog(id);
        return RespBody.getInstance();
    }

    /**
     * 图片编辑页面
     * @return 图片地址
     */
    @PostMapping("/public/system/image/edit_page")
    @Mark(RequestType.PAGE)
    public String editImagePage(Model model, Integer id){
        ImageLog log = imageLogService.getById(id);
        model.addAttribute("log",log);
        return "public/system/image/edit_page";
    }
}
