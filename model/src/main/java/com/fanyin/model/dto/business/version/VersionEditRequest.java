package com.fanyin.model.dto.business.version;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/22 19:32
 */
@Data
public class VersionEditRequest implements Serializable {

    private static final long serialVersionUID = 8670667666853071583L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 版本类型 0:android 2:ios
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
