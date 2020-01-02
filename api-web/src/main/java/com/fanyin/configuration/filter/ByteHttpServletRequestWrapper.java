package com.fanyin.configuration.filter;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @author 二哥很猛
 * @date 2018/8/28 16:42
 */
@Slf4j
public class ByteHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestByte;

    ByteHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.requestByte = this.readByte(request);
    }

    /**
     * 将request中数据读取出来以便于重复利用
     * @param request request
     * @return 将请求中数据转换为字节数组
     */
    private byte[] readByte(HttpServletRequest request){
        try (ServletInputStream inputStream = request.getInputStream();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("过滤器解析request数据异常",e);
            throw new ParameterException(ErrorCode.READ_PARAM_ERROR);
        }
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(getInputStream(), CommonConstant.CHARSET));
    }

    @Override
    public ServletInputStream getInputStream(){

        ByteArrayInputStream bos = new ByteArrayInputStream(requestByte);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return bos.available() <= 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read(){
                return bos.read();
            }
        };
    }
}
