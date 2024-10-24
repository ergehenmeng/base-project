package com.eghm.vo.business.scenic.ticket;

import com.eghm.enums.ref.TicketType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CombineTicketVO {

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票名称")
    private String title;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学")
    private TicketType category;

    @ApiModelProperty(value = "门票介绍")
    private String introduce;

}
