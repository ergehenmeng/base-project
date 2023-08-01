package com.eghm.vo.business.scenic;

import com.eghm.vo.business.activity.ActivityBaseDTO;
import com.eghm.vo.business.scenic.ticket.TicketBaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Data
public class ScenicVO {

    @ApiModelProperty("景区ID")
    private Long id;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("景区等级 5: 5A 4: 4A 3:3A 0:其他")
    private Integer level;

    @ApiModelProperty("景区电话")
    private String phone;

    @ApiModelProperty(value = "景区营业时间")
    private String openTime;

    @ApiModelProperty("景区标签")
    private List<String> tagList;

    @ApiModelProperty("景区信息描述")
    private String depict;

    @ApiModelProperty("距离 单位:m")
    private BigDecimal distance;

    @ApiModelProperty(value = "详细地址(含省市县)")
    private String detailAddress;

    @ApiModelProperty(value = "经度", hidden = true)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", hidden = true)
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区详细介绍信息")
    private String introduce;

    @ApiModelProperty("景区门票列表")
    private List<TicketBaseVO> ticketList;

    @ApiModelProperty("景区活动")
    private List<ActivityBaseDTO> activityList;
}
