package com.eghm.common;

import com.eghm.dto.ext.FilePath;
import com.eghm.dto.ext.UserToken;
import com.eghm.model.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author 二哥很猛
 * @since 2019/11/15 14:49
 */
public interface FileService {

    /**
     * 保存文件
     *
     * @param file 文件
     * @return 文件保存的相对路径
     */
    FilePath saveFile(@NotNull MultipartFile file);

    /**
     * 保存文件
     *
     * @param file   文件
     * @param folder 文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @return 文件保存的相对路径
     */
    FilePath saveFile(@NotNull MultipartFile file, String folder);

    /**
     * 保存文件
     *
     * @param file    文件
     * @param folder  文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(@NotNull MultipartFile file, String folder, long maxSize);

    /**
     * 保存文件
     *
     * @param file    文件
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(@NotNull MultipartFile file, long maxSize);

    /**
     * @author 殿小二
     * @since 2020/8/28
     */
    interface AccessTokenService {

        /**
         * 根据用户信息创建 jwt refresh Token
         *
         * @param user       用户信息
         * @param merchantId 商户id
         * @param authList   菜单权限列表
         * @param dataList   自定义数据权限
         * @return token
         */
        String createToken(SysUser user, Long merchantId, List<String> authList, List<String> dataList);

        /**
         * 解析token
         *
         * @param token token
         * @return 解析出来的用户信息
         */
        Optional<UserToken> parseToken(String token);

        /**
         * 退出登录
         *
         * @param token token
         */
        void logout(String token);
    }
}
