package com.eghm.vo.business.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 拼团活动表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
@Data
public class GroupBookingDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "零售id")
    private Long itemId;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "商品名称")
    private String itemName;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "拼团人数")
    private Integer num;

    @Schema(description = "拼团有效期,单位:分钟")
    private Integer expireTime;

    @Schema(description = "价格信息")
    private List<GroupBookSkuResponse> skuList;

}
