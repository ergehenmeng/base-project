package com.eghm.model.dto.help;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:30
 */
@Data
public class HelpEditRequest implements Serializable {

    private static final long serialVersionUID = 3415746590445570716L;

    /**
     * 主键
     */
    private Integer id;

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
