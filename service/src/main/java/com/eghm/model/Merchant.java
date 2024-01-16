package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("merchant")
@EqualsAndHashCode(callSuper = true)
public class Merchant extends BaseEntity {

    @ApiModelProperty(value = "商家名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String merchantName;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    private Integer type;

    @ApiModelProperty(value = "联系人姓名")
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty("关联的系统用户id")
    private Long userId;

    @ApiModelProperty(value = "微信openid")
    private String openId;

    @ApiModelProperty(value = "微信授权手机号")
    private String authMobile;
}
