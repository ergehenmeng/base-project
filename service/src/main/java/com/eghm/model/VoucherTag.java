package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 餐饮标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("voucher_tag")
public class VoucherTag extends BaseEntity {

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "餐饮商家id")
    private Long restaurantId;

    @ApiModelProperty(value = "所属商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:禁用 1:正常")
    private Boolean state;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
