package com.eghm.vo.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <a href="https://api.kuaidi100.com/document/5f0ffb5ebc8da837cbd8aefc">具体状态查看</a>
 *
 * @author 二哥很猛
 * @since 2023/11/28
 */

@Data
public class ExpressVO {

    @ApiModelProperty("时间")
    private String time;

    @ApiModelProperty("节点内容")
    private String content;
}
