package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "快递单号")
    private String expressNo;

    @Schema(description = "快递公司编码")
    private String expressCode;

    @Schema(description = "收发人手机号")
    private String phone;

    @Schema(description = "物流信息(json)")
    private String content;

}
