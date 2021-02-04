package com.eghm.model.dto.push;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/11/27 13:56
 */
@Data
public class PushTemplateEditRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态 0:关闭 1:开启
     */
    private Boolean state;

    /**
     * 备注
     */
    private String remark;
}
