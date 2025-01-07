package com.eghm.vo.business.scenic;

import com.eghm.convertor.BigDecimalOmitEncoder;
import com.eghm.vo.business.activity.ActivityBaseDTO;
import com.eghm.vo.business.scenic.ticket.TicketBaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Data
public class ScenicDetailVO {

    @Schema(description = "景区ID")
    private Long id;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "景区等级 5:5A 4:4A 3:3A 0:其他")
    private Integer level;

    @Schema(description = "景区电话")
    private String phone;

    @Schema(description = "景区营业时间")
    private String openTime;

    @Schema(description = "景区标签")
    private List<String> tagList;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "景区信息描述")
    private String depict;

    @Schema(description = "距离 单位:m")
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal distance;

    @Schema(description = "详细地址(含省市县)")
    private String detailAddress;

    @Schema(description = "经度", hidden = true)
    private BigDecimal longitude;

    @Schema(description = "纬度", hidden = true)
    private BigDecimal latitude;

    @Schema(description = "景区详细介绍信息")
    private String introduce;

    @Schema(description = "景区门票列表")
    private List<TicketBaseVO> ticketList;

    @Schema(description = "景区活动")
    private List<ActivityBaseDTO> activityList;
}
