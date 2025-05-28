package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
@TableName("express_logistics")
@EqualsAndHashCode(callSuper = true)
public class ExpressLogistics extends BaseEntity {

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "快递公司编码")
    private String expressCode;

    @ApiModelProperty(value = "物流信息(json)")
    private String content;

}
