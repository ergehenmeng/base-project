package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@TableName("auth_config")
@EqualsAndHashCode(callSuper = true)
public class AuthConfigPO extends BaseEntityPO {

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
}


