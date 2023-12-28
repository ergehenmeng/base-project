package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 快递模板表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Data
@TableName("express_template")
@EqualsAndHashCode(callSuper = true)
public class ExpressTemplate extends BaseEntity {

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty(value = "模板名称")
    private String title;

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    private Integer state;

    @ApiModelProperty(value = "计费方式 1:按件数 2:按重量")
    private Integer chargeMode;

}
