package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ConfirmState;
import com.eghm.enums.RoomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 民宿订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
@Data
@TableName("homestay_order")
@EqualsAndHashCode(callSuper = false)
public class HomestayOrder extends BaseEntity {

    @ApiModelProperty(value = "酒店id(冗余字段)")
    private Long homestayId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("房型名称(冗余)")
    private String title;

    @ApiModelProperty("会员id(冗余)")
    private Long memberId;

    @ApiModelProperty(value = "房型id")
    private Long roomId;

    @ApiModelProperty("确认状态 0:待确认 1:确认有房 2:确认无房 3: 自动确认有房 3:自动确认有房")
    private ConfirmState confirmState;

    @ApiModelProperty(value = "入住开始时间(含)")
    private LocalDate startDate;

    @ApiModelProperty(value = "入住结束时间(含)")
    private LocalDate endDate;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty("屋内设施")
    private String infrastructure;

    @ApiModelProperty("确认备注信息")
    private String remark;
}
