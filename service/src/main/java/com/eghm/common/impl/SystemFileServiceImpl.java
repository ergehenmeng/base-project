package com.eghm.common.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.FileService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.FilePath;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.CacheUtil;
import com.eghm.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.eghm.constants.CommonConstant.DAY_MAX_UPLOAD;

/**
 * 保存文件路径格式=根路径+公共路径+文件分类路径+日期+文件名+后缀<br>
 * 返回给调用方的文件地址=公共路径+文件分类路径+日期+文件名+后缀<br>
 * <h4>说明</h4>
 * 根路径由{@link SystemProperties#getUploadPath()}决定<br>
 * 公共路径默认/resource/ 方便nginx或服务做静态资源拦截映射<br>
 * 日期默认yyyyMMdd<br>
 *
 * @author 二哥很猛
 * @since 2019/11/15 15:20
 */
@Slf4j
@AllArgsConstructor
public class SystemFileServiceImpl implements FileService {

    private final SysConfigApi sysConfigApi;

    private final AlarmService alarmService;

    private final SystemProperties systemProperties;

    @Override
    public FilePath saveFile(String key, MultipartFile file) {
        return this.saveFile(key, file, systemProperties.getUploadFolder(), sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, String folder, long maxSize) {
        this.checkSize(file, maxSize);
        Long present = CacheUtil.UPLOAD_LIMIT_CACHE.getIfPresent(key);
        long size = file.getSize() + (present == null ? 0 : present);
        if (size > DAY_MAX_UPLOAD.toBytes()) {
            log.warn("系统单日上传文件超出限制, 用户:[{}] 累计上传:[{}]kb ", key, size / 1024);
            alarmService.sendMsg(String.format("系统单日上传文件超出限制,请注意监控, 用户:%s 今日累计上传:%s", key, (size / 1024 / 1024) + "M"));
        }
        String path = this.doSaveFile(file, folder);
        FilePath build = FilePath.builder().path(path).address(this.getFileAddress()).size(file.getSize()).build();
        CacheUtil.UPLOAD_LIMIT_CACHE.put(key, size);
        return build;
    }

    /**
     * 保存上传的文件
     *
     * @param file   文件
     * @param folder 文件保存的文件夹名称
     * @return 保存文件后的相对路径
     */
    private String doSaveFile(MultipartFile file, String folder) {
        String filePath = this.getFilePath(file.getOriginalFilename(), folder);
        try {
            file.transferTo(this.createFile(filePath));
        } catch (IOException e) {
            log.warn("上传文件保存失败", e);
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        return filePath.replace(File.separator, "/");
    }

    /**
     * 根据文件路径信息创建文件,如果父级目录不存在则创建
     *
     * @param filePath 路径
     * @return 文件
     */
    private File createFile(String filePath) {
        File file = new File(this.getFullPath(filePath));
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            log.error("文件目录创建失败:[{}]", parentFile.getAbsoluteFile());
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        return file;
    }

    /**
     * 计算并生成最终的相对路径
     *
     * @param originalFileName 用户上传的文件名及后缀
     * @param folderName       文件保存的父级文件夹名称
     * @return /resource/image/20191229/3e1be532486249f4b0532a2e594ba187.png
     */
    private String getFilePath(String originalFileName, String folderName) {
        if (originalFileName == null) {
            originalFileName = "default.png";
        }
        // fileName: 3e1be532-4862-49f4-b053-2a2e594ba187.png
        String fileName = IdUtil.fastSimpleUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
        return CommonConstant.ROOT_FOLDER + folderName + File.separator + DateUtil.formatShortLimit(LocalDate.now()) + File.separator + fileName;
    }

    /**
     * 获取根路径
     *
     * @param filePath 文件保存的相对路径,(数据库保存的文件路径) /resource/image/20191229/3e1be53486249f4b0532a2e594ba187.png
     * @return D:/file/data/
     */
    private String getFullPath(String filePath) {
        return systemProperties.getUploadPath() + filePath;
    }

    private String getFileAddress() {
        return sysConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS);
    }
}
