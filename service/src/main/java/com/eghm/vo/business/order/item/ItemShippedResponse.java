package com.eghm.vo.business.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class ItemShippedResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "快递单号")
    private String expressNo;

    @Schema(description = "快递公司编码")
    private String expressCode;

    @Schema(description = "源物流json格式")
    @JsonIgnore
    private String content;

    @Schema(description = "格式化后的物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;

    @Schema(description = "包裹中包含的商品")
    private List<ItemOrderListVO> itemList;

}
