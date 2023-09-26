package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("merchant_user")
public class MerchantUser extends BaseEntity implements Serializable {

    @ApiModelProperty("商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "联系人姓名")
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty("关联的系统用户id")
    private Long userId;
}
