package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("merchant")
@ApiModel(value="Merchant对象", description="商家信息表")
public class Merchant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    private Integer type;

    @ApiModelProperty(value = "联系人姓名")
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty("关联的系统用户id")
    private Long operatorId;
}
