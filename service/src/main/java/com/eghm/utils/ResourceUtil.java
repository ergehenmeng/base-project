package com.eghm.utils;

import cn.hutool.core.io.FileUtil;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.ConfigConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author 二哥很猛
 * @since 2024/9/29
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtil {

    /**
     * 读取文件
     * @param path 路径 <a href="https://www.baidu.com/xxx.png">...</a>
     * @return 字节数组
     */
    public static byte[] readFile(String path) {
        if (path == null) {
            return new byte[0];
        }
        File file;
        try {
            file = SpringContextUtil.getApplicationContext().getResource(getLocalPath(path)).getFile();
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
     * @param path path
     * @return local
     */
    public static String getLocalPath(String path) {
        SysConfigApi sysConfigApi = SpringContextUtil.getApplicationContext().getBean(SysConfigApi.class);
        String fileAddress = sysConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS);
        SystemProperties properties = SpringContextUtil.getApplicationContext().getBean(SystemProperties.class);
        if (path.startsWith(fileAddress)) {
            return "file://" + properties.getUploadPath() + path.replace(fileAddress, "");
        } else {
            return path;
        }
    }
}
