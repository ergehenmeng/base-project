package com.eghm.vo.business.scenic.ticket;

import com.eghm.enums.TicketType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/2/13
 */
@Data
public class TicketBaseResponse {

    @ApiModelProperty(value = "门票id")
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:套票")
    private TicketType category;
}
