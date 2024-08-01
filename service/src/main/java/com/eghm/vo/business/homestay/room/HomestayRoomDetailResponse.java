package com.eghm.vo.business.homestay.room;

import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.RoomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
public class HomestayRoomDetailResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "民宿id")
    private Long homestayId;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @ApiModelProperty("订单确认方式: 1: 自动确认 2:手动确认")
    private Integer confirmType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "屋内设施")
    private String infrastructure;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    private String refundDescribe;
}
