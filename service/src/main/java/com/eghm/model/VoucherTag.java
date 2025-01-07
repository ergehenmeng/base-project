package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "餐饮商家id")
    private Long restaurantId;

    @Schema(description = "所属商户ID")
    private Long merchantId;

    @Schema(description = "状态 0:禁用 1:正常")
    private Boolean state;

    @Schema(description = "备注信息")
    private String remark;
}
