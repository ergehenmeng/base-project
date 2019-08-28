package com.fanyin.controller;

import com.fanyin.constants.ConfigConstant;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/8/28 17:09
 */
@RestController
@Api(tags = "图片上传")
public class ImageController extends AbstractController{

    @Autowired
    private SystemConfigApi systemConfigApi;

    /**
     * 文件上传
     */
    @PostMapping("/upload/image")
    @ApiImplicitParam(name = "image",value = "文件流",required = true)
    @ApiOperation("图片单张上传")
    public RespBody image(@RequestParam("image") MultipartFile image){
        String path = super.saveFile(image);
        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
        map.put("path",path);
        map.put("localhost",systemConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS));
        return RespBody.success(map);
    }
}
