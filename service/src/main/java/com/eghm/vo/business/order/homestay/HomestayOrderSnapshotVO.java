package com.eghm.vo.business.order.homestay;

import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.RoomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class HomestayOrderSnapshotVO {

    @ApiModelProperty("房型名称(冗余)")
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "酒店id")
    private Long homestayId;

    @ApiModelProperty(value = "酒店名称")
    private String homestayName;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0:其他")
    private Integer homestayLevel;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty("退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述")
    private String refundDescribe;

    @ApiModelProperty("屋内设施")
    private String infrastructure;

}
