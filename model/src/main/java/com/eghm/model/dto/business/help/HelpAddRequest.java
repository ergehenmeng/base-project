package com.eghm.model.dto.business.help;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:23
 */
@Data
public class HelpAddRequest implements Serializable {

    private static final long serialVersionUID = -8577111153647154240L;

    /**
     * 帮助说明类型
     */
    private Byte classify;

    /**
     * 是否显示 0:不显示 1:显示
     */
    private Byte state;


    /**
     * 问题
     */
    private String ask;

    /**
     * 答案
     */
    private String answer;
    

    /**
     * 排序
     */
    private int sort;
    

}

