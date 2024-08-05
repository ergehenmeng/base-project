package com.eghm.vo.business.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "零售id")
    private Long itemId;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "拼团人数")
    private Integer num;

    @ApiModelProperty(value = "拼团有效期,单位:分钟")
    private Integer expireTime;

    @ApiModelProperty("价格信息")
    private List<GroupBookSkuResponse> skuList;

}
