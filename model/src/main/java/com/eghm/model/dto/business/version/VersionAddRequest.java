package com.eghm.model.dto.business.version;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/22 14:57
 */
@Data
public class VersionAddRequest implements Serializable {

    private static final long serialVersionUID = 4277066521664563340L;

    /**
     * 版本类型 ANDROID IOS
     */
    private String classify;

    /**
     * 版本号
     */
    private String version;

    /**
     * 是否强制更新
     */
    private Boolean forceUpdate;

    /**
     * 下载地址
     */
    private String url;

    /**
     * 备注信息
     */
    private String remark;
}
