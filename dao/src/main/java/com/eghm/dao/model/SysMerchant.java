package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.MerchantState;
import io.swagger.annotations.ApiModel;
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
@TableName("sys_merchant")
@ApiModel(value="SysMerchant对象", description="商家信息表")
public class SysMerchant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    private Integer type;

    @ApiModelProperty(value = "联系人姓名")
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty(value = "账号名称")
    private String userName;

    @ApiModelProperty(value = "账号密码")
    private String pwd;

    @ApiModelProperty(value = "是否为初始化密码 1:是 0:不是")
    private Boolean initPwd;

    @ApiModelProperty(value = "状态 0:锁定 1:正常 2:销户")
    private MerchantState state;

}
