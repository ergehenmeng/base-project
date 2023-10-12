package com.eghm.vo.business.homestay.room;

import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
public class HomestayRoomResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty("民宿名称")
    private String homestayTitle;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "几室")
    private Integer room;

    @ApiModelProperty(value = "几厅")
    private Integer hall;

    @ApiModelProperty(value = "几厨")
    private Integer kitchen;

    @ApiModelProperty(value = "卫生间数")
    private Integer washroom;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "床数")
    private Integer bed;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Integer roomType;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

}
