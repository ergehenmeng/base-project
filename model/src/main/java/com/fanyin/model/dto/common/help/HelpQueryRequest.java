package com.fanyin.model.dto.common.help;

import com.fanyin.model.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = -3466349098042737884L;

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

}
