package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author 二哥很猛
 */
@Data
@AllArgsConstructor
@TableName("merchant_role")
public class MerchantRole {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long merchantId;

    @ApiModelProperty("角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    public MerchantRole(Long merchantId, Long roleId) {
        this.merchantId = merchantId;
        this.roleId = roleId;
    }
}