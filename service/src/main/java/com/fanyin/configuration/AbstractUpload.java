package com.fanyin.configuration;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.DateUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 保存文件路径格式=根路径+公共路径+文件分类路径+日期+文件名+后缀<br>
 * 返回给调用方的文件地址=公共路径+文件分类路径+日期+文件名+后缀<br>
 * <h4>说明</h4>
 * 根路径由{@link ApplicationProperties#getUploadDir()}决定<br>
 * 公共路径默认/upload/ 方便nginx或服务做静态资源拦截映射<br>
 * 文件分类路径{@link AbstractUpload#getFolderName()}决定<br>
 * 日期默认yyyyMMdd<br>
 * @author 二哥很猛
 * @date 2018/11/27 15:20
 */
@Slf4j
public abstract class AbstractUpload{

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * 系统路径分隔符
     */
    private static final String SPT = File.separator;

    /**
     * 默认配置,如果不指定则会自动创建文件
     */
    public static final String DEFAULT_DIR = SPT + "upload" + SPT;

    /**
     * 单文件上传 最大5M
     */
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    /**
     * 单次上传大小限制为 20M
     */
    private static final long TOTAL_MAX_SIZE = 20 * 1024 * 1024;

    /**
     * 默认文件夹名称
     */
    private static final String DEFAULT_FOLDER_NAME = "image";


    /**
     * 保存单文件
     * @param file    上传的文件
     * @param maxSize 单文件最大 单位:b 注意:系统级单文件大小需要在application.properties中配置 默认1M
     * @return 保存文件后的相对路径
     */
    protected String saveFile(MultipartFile file, long maxSize){
        if(maxSize < file.getSize()){
            log.warn("上传文件过大:[{}]",file.getSize());
            throw new BusinessException(ErrorCodeEnum.UPLOAD_TOO_BIG.getCode(),"文件过大,单文件最大:" + maxSize / 1048576 + "M");
        }
        return this.doSaveFile(file);
    }

    /**
     * 保存文件 最大支持5M
     * @param file 文件
     * @return 保存后的相对路径
     */
    protected String saveFile(MultipartFile file){
        return this.saveFile(file,MAX_FILE_SIZE);
    }

    /**
     * 保存上传的文件
     * @param file 文件
     * @return 保存文件后的相对路径
     */
    private String doSaveFile(MultipartFile file){
        String filePath = createFilePath(file.getOriginalFilename());
        try {
            file.transferTo(createFile(filePath));
        } catch (IOException e) {
            log.warn("上传文件保存失败",e);
            throw new BusinessException(ErrorCodeEnum.FILE_SAVE_ERROR);
        }
        return filePath.replaceAll("\\\\","/");
    }

    /**
     * 根据文件路径信息创建文件,如果父级目录不存在则创建
     * @param filePath 路径
     * @return 文件
     */
    private File createFile(String filePath){
        File file = new File(getBaseDir() + filePath);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            if(!parentFile.mkdirs()){
                log.error("文件目录创建失败:[{}]",parentFile.getAbsoluteFile());
                throw new BusinessException(ErrorCodeEnum.FILE_SAVE_ERROR);
            }
        }
        return file;
    }

    /**
     * 多文件上传保存 默认最大总文件为:20M
     * @param files 待上传的文件列表
     * @return 保存文件后的相对路径
     */
    protected List<String> saveFiles(List<MultipartFile> files){
        return this.saveFiles(files,TOTAL_MAX_SIZE);
    }

    /**
     * 多文件上传保存
     * @param files 上传文件的文件
     * @param maxSize 所有文件总大小限制 单位:b
     * @return 保存文件后的相对路径
     */
    protected List<String> saveFiles(List<MultipartFile> files, long maxSize){

        long requestSize = 0;
        //本次遍历 主要防止如果部分文件保存,而最后文件过大导致浪费空间
        for(MultipartFile file : files){
            requestSize += file.getSize();
        }

        if(requestSize > maxSize){
            throw new BusinessException(ErrorCodeEnum.UPLOAD_TOO_BIG.getCode(),"文件过大,总文件大小:" + maxSize / (1024 * 1024) + "M");
        }

        List<String> paths = Lists.newArrayList();
        for(MultipartFile file : files){
            paths.add(this.doSaveFile(file));
        }
        return paths;
    }

    /**
     * 获取基础路径
     * @return 基础路径 D:/file/data或者
     */
    private String getBaseDir(){
        return applicationProperties.getUploadDir();
    }


    /**
     * 创建文件路径信息
     * @param fileFullName 文件全名 包含后缀
     * @return 全路径
     */
    private String createFilePath(String fileFullName){
        return DEFAULT_DIR + this.getFolderName() + SPT + DateUtil.formatShortLimit(DateUtil.getNow()) + SPT + this.createFileName(fileFullName);
    }

    /**
     * 自动生成本地保存的文件名 包含后缀名 uuid方式
     * @param originalFileName 原始文件名
     * @return 例如:3e1be532-4862-49f4-b053-2a2e594ba187.png
     */
    private String createFileName(String originalFileName){
        return UUID.randomUUID().toString() + getSuffix(originalFileName);
    }

    /**
     * 获取文件后缀名,注意原始名可能包含路径信息 在opera浏览器上
     * @param originalFileName 上传文件的原始名
     * @return string
     */
    private String getSuffix(String originalFileName){
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 获取项目的根目录 子类可覆盖该方法用于指定上传文件目录
     * @return 目前为空
     */
    protected String getFolderName(){
        return DEFAULT_FOLDER_NAME;
    }

}
