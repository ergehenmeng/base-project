package com.eghm.operate.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthConfig extends BaseEntity {

    /** 单位名称 */
    private String title;

    /** appId */
    private String appId;

    /** 秘钥 */
    private String appSecret;

    /** 过期时间 */
    private LocalDate expireDate;

    /** 邮箱 */
    private String email;

    /** 备注信息 */
    private String remark;

    /**
     * 初始化第三方授权配置.
     *
     * @param appId     appId
     * @param appSecret appSecret
     * @param today     当前日期
     */
    public void initialize(String appId, String appSecret, LocalDate today) {
        this.appId = appId;
        this.appSecret = appSecret;
        if (this.expireDate == null) {
            this.expireDate = today.plusYears(1);
        }
    }

    /**
     * 重置密钥.
     *
     * @param appSecret 新密钥
     */
    public void resetSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
