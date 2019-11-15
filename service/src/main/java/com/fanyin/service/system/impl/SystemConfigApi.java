package com.fanyin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;
import com.fanyin.service.system.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统参数公用Api接口
 * 主要原因缓存注解Aop同方法调用失效问题
 * @see SystemConfigServiceImpl
 * @author 二哥很猛
 * @date 2018/9/12 14:36
 */
@Service("systemConfigApi")
@Transactional(rollbackFor = RuntimeException.class,readOnly = true)
@Slf4j
public class SystemConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 根据nid获取系统参数配置信息的值
     * @param nid 唯一nid
     * @return 系统参数结果值string
     */
    public String getString(String nid){
        String content = systemConfigService.getByNid(nid);
        if (content == null){
            throw new ParameterException(ErrorCode.CONFIG_NOT_FOUND_ERROR);
        }
        return content;
    }

    /**
     * 根据nid获取系统参数配置信息的值,支持以下类型(yes,true,on,y,t,n,f,no,off,false,1,0)
     * @param nid 唯一nid
     * @return 系统参数结果值boolean
     */
    public boolean getBoolean(String nid){
        String value = this.getString(nid);
        try {
            return BooleanUtils.toBoolean(Integer.parseInt(value));
        }catch (Exception e){
            return BooleanUtils.toBoolean(value);
        }
    }

    /**
     * 根据nid获取系统参数配置信息
     * @param nid 唯一nid
     * @return  系统参数结果 double
     */
    public double getDouble(String nid){
        String value = this.getString(nid);
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            log.error("系统参数转double异常 [{}]",value);
        }
        return 0D;
    }

    /**
     * 根据nid获取系统参数配置信息的值
     * @param nid 唯一nid
     * @return 系统参数结果值int 如果转换失败为0
     */
    public int getInt(String nid){
        String value = this.getString(nid);
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            log.error("系统参数转int异常 [{}]",value);
            return 0;
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     * @param nid 唯一nid
     * @return 系统参数结果值json,如果异常则抛出
     */
    public JSONObject getJson(String nid){
        String value = this.getString(nid);
        try {
            return JSONObject.parseObject(value);
        }catch (Exception e){
            log.error("系统参数转json异常 [{}]",value);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    /**
     * 根据nid获取系统参数配置信息的值
     * @param nid 唯一nid
     * @param cls 要转换的类型
     * @return 系统参数结果值class,如果异常则抛出
     */
    public <T> T getClass(String nid, Class<T> cls){
        String value = this.getString(nid);
        try {
            return JSONObject.parseObject(value,cls);
        }catch (Exception e){
            log.error("系统参数转对象异常 [{}]",value);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }
}
