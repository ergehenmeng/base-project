package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@TableName("merchant_user")
@EqualsAndHashCode(callSuper = true)
public class MerchantUser extends BaseEntity {

    @Schema(description = "商户ID")
    private Long merchantId;

    @Schema(description = "联系人姓名")
    private String nickName;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "关联的系统用户id")
    private Long userId;

    @Schema(description = "备注信息")
    private String remark;
}
