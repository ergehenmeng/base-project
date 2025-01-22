package com.eghm.vo.business.homestay.room;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/12
 */

@Data
public class HomestayRoomVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "房型名称")
    private String title;

    @Schema(description = "是否为推荐房型 true:是 false:不是")
    private Boolean recommend;

    @Schema(description = "面积")
    private Integer dimension;

    @Schema(description = "居住人数")
    private Integer resident;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RefundType roomType;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "屋内设施")
    private String infrastructure;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "退款描述")
    private String refundDescribe;

    @Schema(description = "最低价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;
}
