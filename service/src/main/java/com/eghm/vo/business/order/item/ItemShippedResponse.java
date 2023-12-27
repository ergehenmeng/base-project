package com.eghm.vo.business.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class ItemShippedResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("快递单号")
    private String expressNo;

    @ApiModelProperty("快递公司编码")
    private String expressCode;

    @ApiModelProperty("源物流json格式")
    @JsonIgnore
    private String content;

    @ApiModelProperty("格式化后的物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;

    @ApiModelProperty("包裹中包含的商品")
    private List<ItemOrderListVO> itemList;

}
