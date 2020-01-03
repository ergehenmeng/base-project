package com.eghm.model.dto.business.image;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/29 17:00
 */
@Data
public class ImageEditRequest implements Serializable {

    private static final long serialVersionUID = 4134425550056402012L;

    /**
     * 图片名称
     */
    private String title;

    /**
     * 投标类型
     */
    private Byte classify;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 主键
     */
    private Integer id;
}
