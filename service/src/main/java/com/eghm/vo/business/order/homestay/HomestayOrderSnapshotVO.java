package com.eghm.vo.business.order.homestay;

import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class HomestayOrderSnapshotVO {

    @Schema(description = "房型名称(冗余)")
    private String title;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "酒店id")
    private Long homestayId;

    @Schema(description = "酒店名称")
    private String homestayName;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 0:其他")
    private Integer homestayLevel;

    @Schema(description = "面积")
    private Integer dimension;

    @Schema(description = "居住人数")
    private Integer resident;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "退款描述")
    private String refundDescribe;

    @Schema(description = "屋内设施")
    private String infrastructure;

}
