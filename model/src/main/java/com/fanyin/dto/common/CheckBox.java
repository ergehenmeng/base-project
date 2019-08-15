package com.fanyin.dto.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 复选框下拉列表
 * @author 二哥很猛
 * @date 2018/11/30 15:19
 */
@Data
public class CheckBox implements Serializable {
    private static final long serialVersionUID = -9147788551948904950L;

    /**
     * 隐藏值
     */
    private int hide;

    /**
     * 显示值
     */
    private String show;

    public CheckBox() {
    }

    public CheckBox(int hide, String show) {
        this.hide = hide;
        this.show = show;
    }
}
