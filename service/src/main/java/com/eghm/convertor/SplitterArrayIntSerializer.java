package com.eghm.convertor;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/1/17
 */
public class SplitterArrayIntSerializer extends SplitterArraySerializer {

    /**
     * 写数据
     * 
     * @param gen gen 
     * @param value value
     * @throws IOException e
     */
    @Override
    public void doWrite(JsonGenerator gen, String value) throws IOException {
        gen.writeNumber(Integer.parseInt(value));
    }
}
