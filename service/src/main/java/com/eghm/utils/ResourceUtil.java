package com.eghm.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;

/**
 * @author 二哥很猛
 * @since 2024/9/29
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtil {

    /**
     * 读取文件
     *
     * @param path 路径 <a href="https://www.baidu.com/xxx.png">...</a>
     * @return 字节数组
     */
    public static byte[] readFile(String path) {
        if (path == null) {
            return new byte[0];
        }
        File file;
        try {
            file = SpringUtil.getApplicationContext().getResource(getLocalPath(path)).getFile();
        } catch (Exception e) {
            log.error("读取本地文件异常 [{}]", path, e);
            return new byte[0];
        }
        if (!file.exists()) {
            return new byte[0];
        }
        return FileUtil.readBytes(file);
    }

    /**
     * 将远程地址转换为本地文件路径, 如：<a href="https://127.0.0.1:8080/xxx.png">https://127.0.0.1:8080/xxx.png</a> 转换为 file:///D:/xxx.png
     * 注意: 如果是网络图片,则不需要转换机器本地路径
     *
     * @param path path
     * @return local
     */
    public static String getLocalPath(String path) {
        SysConfigApi sysConfigApi = SpringUtil.getBean(SysConfigApi.class);
        String fileAddress = sysConfigApi.getString(ConfigConstant.FILE_SERVER_HOST);
        SystemProperties properties = SpringUtil.getBean(SystemProperties.class);
        if (path.startsWith(fileAddress)) {
            return "file://" + properties.getUploadPath() + path.replace(fileAddress, "");
        } else {
            return path;
        }
    }

    /**
     * 获取文件
     *
     * @param path path classpath下
     * @return local
     */
    public static InputStream getInputStream(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path, ResourceUtils.class.getClassLoader());
            return resource.getInputStream();
        } catch (Exception e) {
            log.error("获取文件失败 [{}]", path, e);
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }
    }
}
