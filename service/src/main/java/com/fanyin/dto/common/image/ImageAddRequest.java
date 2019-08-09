package com.fanyin.dto.common.image;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/28 17:33
 */
@Data
public class ImageAddRequest implements Serializable {
    private static final long serialVersionUID = 3775459871066834161L;

    /**
     * 图片名称
     */
    private String title;

    /**
     * 图片分类
     */
    private Byte classify;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 地址
     */
    private String url;
}
