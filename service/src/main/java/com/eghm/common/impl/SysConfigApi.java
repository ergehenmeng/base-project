package com.eghm.common.impl;

import com.eghm.cache.CacheProxyService;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.eghm.service.sys.impl.SysConfigServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统参数公用Api接口
 * 主要原因缓存注解Aop同方法调用失效问题
 *
 * @author 二哥很猛
 * @since 2018/9/12 14:36
 * @see SysConfigServiceImpl
 */
@Slf4j
@Service("sysConfigApi")
@AllArgsConstructor
public class SysConfigApi {

    private final CacheProxyService cacheProxyService;

    /**
     * 根据nid获取系统参数配置信息的值
     *
     * @param nid 唯一nid
     * @return 系统参数结果值string
     */
    public String getString(String nid) {
        String content = cacheProxyService.getConfigByNid(nid);
        if (content == null) {
            log.warn("系统参数未找到 nid:[{}]", nid);
            throw new ParameterException(ErrorCode.CONFIG_NOT_FOUND_ERROR);
        }
        return content;
    }

    /**
     * 根据nid获取系统参数配置信息的值,支持以下类型(yes,true,on,y,t,n,f,no,off,false,1,0)
     *
     * @param nid 唯一nid
     * @return 系统参数结果值boolean
     */
    public boolean getBoolean(String nid) {
        String value = this.getString(nid);
        try {
            return BooleanUtils.toBoolean(Integer.parseInt(value));
        } catch (Exception e) {
            return BooleanUtils.toBoolean(value);
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     *
     * @param nid 唯一nid
     * @return 系统参数结果值int 如果转换失败为0
     */
    public int getInt(String nid) {
        String value = this.getString(nid);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.warn("系统参数转int异常(无默认值) [{}]", value);
            return 0;
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     *
     * @param nid          唯一nid
     * @param defaultValue 解析失败时,采用默认值
     * @return 系统参数结果值int 如果转换失败为0
     */
    public int getInt(String nid, int defaultValue) {
        String value = this.getString(nid);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.warn("系统参数转int异常(有默认值) [{}]", value);
            return defaultValue;
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     *
     * @param nid 唯一nid
     * @return 系统参数结果值long 如果转换失败为0
     */
    public long getLong(String nid) {
        String value = this.getString(nid);
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            log.warn("系统参数转long异常(无默认值) [{}]", value);
            return 0L;
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     *
     * @param nid          唯一nid
     * @param defaultValue 解析错误时 采用默认值
     * @return 系统参数结果值long 如果转换失败为0
     */
    public long getLong(String nid, long defaultValue) {
        String value = this.getString(nid);
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            log.warn("系统参数转long异常(有默认值) [{}]", value);
            return defaultValue;
        }
    }
}
