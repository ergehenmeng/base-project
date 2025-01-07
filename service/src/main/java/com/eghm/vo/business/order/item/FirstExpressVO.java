package com.eghm.vo.business.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/28
 */

@Data
public class FirstExpressVO {

    @Schema(description = "快递id")
    private Long id;

    @Schema(description = "快递内容(最新节点)")
    private String content;
}
